package com.AscalonPS.engine.task.impl;

import com.AscalonPS.engine.task.Task;
import com.AscalonPS.engine.task.TaskManager;
import com.AscalonPS.world.content.skill.impl.hunter.Hunter;
import com.AscalonPS.world.content.skill.impl.hunter.Trap;
import com.AscalonPS.world.content.skill.impl.hunter.TrapExecution;

import java.util.Iterator;

public class HunterTrapsTask extends Task {

    public HunterTrapsTask() {
        super(1);
    }

    @Override
    protected void execute() {
        final Iterator<Trap> iterator = Hunter.traps.iterator();
        while (iterator.hasNext()) {
            final Trap trap = iterator.next();
            if (trap == null)
                continue;
            if (trap.getOwner() == null || !trap.getOwner().isRegistered())
                Hunter.deregister(trap);
            TrapExecution.setTrapProcess(trap);
            TrapExecution.trapTimerManagement(trap);
        }
        if (Hunter.traps.isEmpty())
            stop();
    }

    @Override
    public void stop() {
        setEventRunning(false);
        running = false;
    }

    private static boolean running;

    public static void fireTask() {
        if (running)
            return;
        running = true;
        TaskManager.submit(new HunterTrapsTask());
    }
}
