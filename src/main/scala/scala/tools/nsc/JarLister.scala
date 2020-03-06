/*
 * JarLister.scala
 *
 * Copyright (C) 2015 Raphael Jolly
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/
 */

package scala.tools.nsc

import java.io.{File, FileInputStream, FileOutputStream, InputStream, OutputStream, IOException}
import java.util.jar.{JarFile, JarEntry, JarOutputStream, Attributes}
import scala.collection.convert.WrapAsScala
import WrapAsScala.{enumerationAsScalaIterator, mapAsScalaMap}

/** A class that lists a jar's classes into its manifest.
  * Used to make libraries visible to the interpreter
  * when run as a script engine.
  */
class JarLister {

  def process(args: Array[String]): Boolean = {
    if (args.length == 0 || args(0) == "-help") {
      Console.println("""Usage: jarlister [<options>] <jar name>
   or  jarlister -help

Options:

 -o <file>  specify output jar file
""")
      return true
    }
    if (args(0) == "-o") process(args(2), args(1)) else process(args(0), "")
  }

  def process(name: String, output: String): Boolean = {
    val listedName = if (output == "") name + ".lst" else output
    val file = new File(name);
    val jarFile = new JarFile(file);
    val listedFile = new File(listedName);

    val manifest = jarFile.getManifest
    val mfEntries = manifest.getEntries

    for (entry <- jarFile.entries) if (entry.getName.endsWith(".class") && !mfEntries.contains(entry.getName)) {
      mfEntries(entry.getName) = new Attributes
    }

    val fos = new FileOutputStream(listedFile)
    val jos = new JarOutputStream(fos, manifest)

    for (entry <- jarFile.entries) if (!entry.getName.equalsIgnoreCase(JarFile.MANIFEST_NAME)) {
      jos.putNextEntry(entry)
      writeBytes(jarFile, entry, jos)
    }

    jos.finish
    fos.close
    jarFile.close

    if (output == "") {
      val is = new FileInputStream(listedFile)
      val os = new FileOutputStream(file)
      pipe(is, os)
      os.close
      is.close
      listedFile.delete
    }
    return true
  }

  val buffer = new Array[Byte](8192)
  def writeBytes(f: JarFile, e: JarEntry, os: JarOutputStream) {
    val is = f.getInputStream(e)
    var left = e.getSize
    var n = 0
    while(left > 0) {
      n = is.read(buffer, 0, buffer.length)
      if (n == -1) throw new IOException("read error")
      os.write(buffer, 0, n)
      left -= n
    }
  }
  def pipe(is: InputStream, os: OutputStream) {
    var n = is.read(buffer)
    while(n > -1) {
      os.write(buffer, 0, n)
      n = is.read(buffer)
    }
  }
}

object JarLister extends JarLister {
  def main(args: Array[String]) {
    if (!process(args))
      sys.exit(1)
  }
}
