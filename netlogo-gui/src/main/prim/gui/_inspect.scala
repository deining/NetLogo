// (C) Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.prim.gui

import org.nlogo.core.I18N
import org.nlogo.nvm.{ Command, Context, RuntimePrimitiveException }

class _inspect extends Command {

  override def perform(context: Context): Unit = {
    val agent = argEvalAgent(context, 0)
    if (agent.id == -1)
      throw new RuntimePrimitiveException(context, this,
        I18N.errors.getN("org.nlogo.$common.thatAgentIsDead", agent.classDisplayName))
    org.nlogo.awt.EventQueue.invokeLater(
      new Runnable {
        override def run(): Unit = {
          // we usually use a default radius of 3, but that doesn't work when the world has a radius
          // of less than 3. so simply take the minimum. - JC 7/1/10
            val minWidthOrHeight =
              (workspace.world.worldWidth  / 2) min
              (workspace.world.worldHeight / 2)
            val radius = 3 min (minWidthOrHeight / 2)
            workspace.inspectAgent(agent.kind, agent, radius)
        }})
    context.ip = next
  }
}
