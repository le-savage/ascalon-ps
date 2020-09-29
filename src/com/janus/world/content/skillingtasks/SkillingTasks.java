package com.janus.world.content.skillingtasks;

import com.janus.model.Skill;
import com.janus.util.Misc;
import com.janus.world.content.dialogue.DialogueManager;
import com.janus.world.content.questtab.QuestTab;
import com.janus.world.entity.impl.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SkillingTasks {

    public static int requiredAmountToCompleteTask(Player player, Tasks task) {
        return (task.progressData[1] * taskRequirementMultiplier(player, task));
    }

    public static void updateInterface(Player player) { // TODO Add something to the quest panel to track progress
        QuestTab.refreshPanel();
    }

    public static List<SkillingTasks.Tasks> tasksList = new ArrayList<>();

    public static void loadData() {
        Collections.addAll(tasksList, Tasks.values());
    }

    public static List<SkillingTasks.Tasks> filterBySkill(com.janus.model.Skill skill) {
        System.out.println("Skill chosen: " + skill.toString());
        List<SkillingTasks.Tasks> list = new ArrayList<>();
        tasksList.forEach(task -> {
            if (task.getSkill().toString().equals(skill.toString()))
                list.add(task);
        });
        return list;
    }

    @Getter
    @Setter
    public static Tasks currentTask; // Created to allow restoration upon restart

    public static void assignTask(Player player, Skill skill) {

        /** Picking a random task from the filtered list..
         *  We use the size of the list -1 for the random range!
         */
        Tasks task = filterBySkill(skill).get(Misc.random(filterBySkill(skill).size() - 1));
        currentTask = task; // Assign the current task
        player.setSkillTaskOrdinal(task.ordinal()); // To save the progress upon a relog
        player.getPA().sendMessage("Current task: " + task.getDescription());
        task.setActive(true); // Enables the task to allow progress
        DialogueManager.start(player, TaskDialogue.assignTask(player)); // Show the finished dialogue
        System.out.println("TASK DESC: " + currentTask.description);
    }

    private static int taskRequirementMultiplier(Player player, Tasks currentTask) { // Multiply the required amount of a task based on difficulty
        String taskDifficulty = player.getTaskDifficulty();
        if (currentTask.progressData == null)
            return 0;
        if (taskDifficulty.equalsIgnoreCase("easy"))
            return 1;
        if (taskDifficulty.equalsIgnoreCase("medium"))
            return 2;
        if (taskDifficulty.equalsIgnoreCase("hard"))
            return 3;
        if (taskDifficulty.equalsIgnoreCase("expert"))
            return 4;
        if (taskDifficulty.equalsIgnoreCase("elite"))
            return 5;

        return 0;
    }

    private static int rewardMultiplier(Player player, Tasks currentTask) { // Multiply the required amount of a task based on difficulty
        String taskDifficulty = player.getTaskDifficulty();
        if (currentTask.progressData == null)
            return 0;
        if (taskDifficulty.equalsIgnoreCase("easy"))
            return 1;
        if (taskDifficulty.equalsIgnoreCase("medium"))
            return 3;
        if (taskDifficulty.equalsIgnoreCase("hard"))
            return 4;
        if (taskDifficulty.equalsIgnoreCase("expert"))
            return 5;
        if (taskDifficulty.equalsIgnoreCase("elite"))
            return 6;

        return 0;
    }

    public static void doProgress(Player player, Tasks task) {
        if (!task.isActive()) // Stops progress happening on unassigned tasks
            return;
        System.out.println(task.description);
        System.out.println(task.skill);
        System.out.println(task.points);
        System.out.println(Arrays.toString(task.progressData));
        if (task.progressData != null) {
            int progressIndex = task.progressData[0];
            int amountNeeded = (task.progressData[1]) * taskRequirementMultiplier(player, task);
            int currentProgress = player.getSkillTaskAttributes().getProgress()[progressIndex];
            System.out.println("Prog index: " + progressIndex);
            System.out.println("Amt Needed: " + amountNeeded);
            System.out.println("Current Prog: " + currentProgress);
            if ((currentProgress + 1) < amountNeeded) {
                player.getSkillTaskAttributes().getProgress()[progressIndex] = currentProgress + 1;
                if (currentProgress == 0)
                    System.out.println("Current progress 0");
            } else {
                finishTask(player, task);
            }
        }
    }

    public static void finishTask(Player player, Tasks task) {
        if (player.getSkillTaskAttributes().getCompleted()[task.ordinal()])
            return;
        if (!task.isActive())
            return;
        giveReward(player, task);
        task.setActive(false);
        currentTask = null;
        System.out.println("Finishing Task: " + task.getDescription() + " for " + player.getUsername());
        DialogueManager.start(player, TaskDialogue.finishedTask(player)); // Show the finished dialogue
        player.setSkillTaskOrdinal(0);
    }

    public static void restoreTaskOnLogin(Player player) {
        setCurrentTask(tasksList.get(player.getSkillTaskOrdinal()));
        System.out.println("task restored : " + currentTask.description);
    }

    public static void giveReward(Player player, Tasks task) {
        int currentPoints = player.getSkillTaskPoints();
        int rewardMultiplier = rewardMultiplier(player, task); // Used to multiply pts and XP rewards
        player.setSkillTaskPoints(currentPoints + (task.getPoints() * rewardMultiplier)); // Awarding Points
        player.getSkillManager().addExperienceWithNoBonuses(Skill.forName(task.skill.toString()), (task.xpReward * rewardMultiplier)); // Awarding XP
    }

    @AllArgsConstructor // This creates the whole constructor that we usually manually type
    @Getter
    public enum Tasks {

        // 'Your task is to.... Bla Bla .. 50 times'
        COMPLETE_EDGE_AGILITY_COURSE(Skill.AGILITY,  "Complete The Edgeville Agility Course", null, 4, 5000, false),
        USE_ANY_OBSTACLE(Skill.AGILITY,  "Use any obstacle", new int[]{0, 100}, 5, 5000, false),
        TEST_THREE(Skill.AGILITY,  "Agility", new int[]{1, 4}, 3, 30000, false),
        TEST_ONEE(Skill.ATTACK,  "Attack", null, 4, 30000, false),
        TEST_TWOE(Skill.PRAYER,  "Prayer", new int[]{0, 3}, 5, 30000, false),
        TEST_THREEE(Skill.SMITHING,  "Smithing", new int[]{1, 4}, 3, 30000, false),
        TEST_ONEEEE(Skill.WOODCUTTING,  "Woodcutting", null, 4, 30000, false),
        TEST_TWEO(Skill.HUNTER,  "Hunter", new int[]{0, 3}, 5, 30000, false),
        TEST_THEEREE(Skill.MINING,  "Mining", new int[]{1, 4}, 3, 30000, false),
        ;


        private Skill skill;
        private String description;
        private int[] progressData; // progressData[0] is a unique index number
        private int points;
        private int xpReward;
        @Setter
        private boolean active;

    }

    private static int tasksWithProgress = 0;

    public static void tasksWithProgress(Tasks task) {
        for (Tasks ignored : Tasks.values()) {
            if (task.progressData != null) {
                tasksWithProgress++;
                System.out.println("Tasks with progress called. Result is: "+tasksWithProgress);
            }
        }
    }

    @Getter
    @Setter
    public static class SkillingTaskAttributes {

        private boolean[] completed = new boolean[Tasks.values().length];

        private int[] progress = new int[tasksWithProgress]; // TODO Keep this updated with total tasks which use data
    }
}
