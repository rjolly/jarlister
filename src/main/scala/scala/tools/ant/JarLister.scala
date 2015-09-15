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

package scala.tools.ant

import java.io.File
import org.apache.tools.ant.BuildException
import org.apache.tools.ant.taskdefs.MatchingTask

/** An Ant task that lists a jar's classes into its manifest.
 *
 *  This task can take the following parameters as attributes:
 *  - `file` (mandatory),
 *  - `output`.
 *
 * @author  Raphael Jolly
 * @version 1.0
 */
class JarLister extends MatchingTask {

/*============================================================================*\
**                             Ant user-properties                            **
\*============================================================================*/

  /** The path to the jar file. */
  private var file: Option[File] = None

  /** The path to the output file. */
  private var output: Option[File] = None

/*============================================================================*\
**                             Properties setters                             **
\*============================================================================*/

  /** Sets the file attribute. */
  def setFile(input: File) =
    file = Some(input)

  /** Sets the output attribute. */
  def setOutput(input: File) =
    output = Some(input)

/*============================================================================*\
**                           The big execute method                           **
\*============================================================================*/

  /** Performs the jar listing. */
  override def execute() = {
    // Tests if all mandatory attributes are set and valid.
    if (file.isEmpty) buildError("Attribute 'file' is not set.")

    scala.tools.nsc.JarLister.process(file.get.getPath, if (output.isEmpty) "" else output.get.getPath)
  }

  protected def buildError(message: String): Nothing =
    throw new BuildException(message, getLocation())
}
