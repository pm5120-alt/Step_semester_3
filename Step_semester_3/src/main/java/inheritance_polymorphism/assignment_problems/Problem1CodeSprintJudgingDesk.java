package inheritance_polymorphism.assignment_problems;

import java.util.*;

interface ScoringRule8A {
    double calculate(double idea, double execution, double presentation);
    String getTrackName();
}

class InnovationScoringRule8A implements ScoringRule8A {
    public double calculate(double idea, double execution, double presentation) {
        return idea * 0.50 + execution * 0.30 + presentation * 0.20;
    }
    public String getTrackName() { return "Innovation"; }
}

class OpenScoringRule8A implements ScoringRule8A {
    public double calculate(double idea, double execution, double presentation) {
        return (idea + execution + presentation) / 3.0;
    }
    public String getTrackName() { return "Open"; }
}

class Student8A {
    private final String studentId;
    private final String name;

    public Student8A(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }
}

class Team8A {
    private final String name;
    private final List<Student8A> members;
    private final ScoringRule8A scoringRule;
    private Project8A project;

    public Team8A(String name, List<Student8A> members, ScoringRule8A scoringRule) {
        if (members.size() < 2 || members.size() > 4) {
            throw new IllegalArgumentException("A team must have 2 to 4 members.");
        }
        if (members.stream().map(Student8A::getStudentId).distinct().count() != members.size()) {
            throw new IllegalArgumentException("A team cannot contain the same student twice.");
        }
        this.name = name;
        this.members = List.copyOf(members);
        this.scoringRule = scoringRule;
    }

    public void submitProject(Project8A project) {
        if (this.project != null) {
            throw new IllegalStateException("A team can submit only one project.");
        }
        this.project = project;
    }

    public String getName() { return name; }
    public List<Student8A> getMembers() { return members; }
    public ScoringRule8A getScoringRule() { return scoringRule; }
    public Project8A getProject() { return project; }
}

class Project8A {
    private final String title;
    public Project8A(String title) { this.title = title; }
    public String getTitle() { return title; }
}

class Judge8A {
    private final String name;
    public Judge8A(String name) { this.name = name; }
    public String getName() { return name; }
}

class Score8A {
    private final Judge8A judge;
    private final Project8A project;
    private final double idea;
    private final double execution;
    private final double presentation;

    public Score8A(Judge8A judge, Project8A project, double idea,
                   double execution, double presentation) {
        validate(idea);
        validate(execution);
        validate(presentation);
        this.judge = judge;
        this.project = project;
        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
    }

    private void validate(double rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Ratings must be between 0 and 10.");
        }
    }

    public double finalScore(ScoringRule8A rule) {
        return rule.calculate(idea, execution, presentation);
    }

    public Project8A getProject() { return project; }
    public Judge8A getJudge() { return judge; }
}

class Hackathon8A {
    enum State { OPEN, JUDGING, PUBLISHED }

    private final String name;
    private final List<Team8A> teams = new ArrayList<>();
    private final Set<String> teamStudentIds = new HashSet<>();
    private final List<Score8A> scores = new ArrayList<>();
    private State state = State.OPEN;

    public Hackathon8A(String name) {
        this.name = name;
    }

    public void registerTeam(Team8A team) {
        if (state != State.OPEN) {
            throw new IllegalStateException("Registration is closed.");
        }

        Set<String> newMemberIds = new HashSet<>();
        for (Student8A member : team.getMembers()) {
            if (!newMemberIds.add(member.getStudentId())) {
                throw new IllegalStateException(
                        member.getName() + " appears more than once in the team.");
            }
            if (teamStudentIds.contains(member.getStudentId())) {
                throw new IllegalStateException(
                        member.getName() + " already belongs to a team in this hackathon.");
            }
        }

        teamStudentIds.addAll(newMemberIds);
        teams.add(team);
        System.out.println("Team " + team.getName() + " registered ("
                + team.getMembers().size() + " members, "
                + team.getScoringRule().getTrackName() + " track).");
    }

    public void submitProject(Team8A team, Project8A project) {
        ensureTeamExists(team);
        team.submitProject(project);
        System.out.println("Project '" + project.getTitle()
                + "' submitted by " + team.getName() + ".");
    }

    public void startJudging() {
        if (state != State.OPEN) throw new IllegalStateException("Invalid state transition.");
        state = State.JUDGING;
    }

    public void recordScore(Score8A score) {
        if (state != State.JUDGING) throw new IllegalStateException("Scoring is not open.");
        scores.add(score);
        System.out.println("Score recorded for '" + score.getProject().getTitle() + "'.");
    }

    public void publishResults() {
        if (state != State.JUDGING) throw new IllegalStateException("Invalid publish transition.");
        state = State.PUBLISHED;
        System.out.println("Results published.");
    }

    public void rescore(Score8A score) {
        if (state == State.PUBLISHED) {
            throw new IllegalStateException("Results have already been published.");
        }
        scores.removeIf(existing -> existing.getProject() == score.getProject()
                && existing.getJudge() == score.getJudge());
        scores.add(score);
    }

    private void ensureTeamExists(Team8A team) {
        if (!teams.contains(team)) throw new IllegalArgumentException("Team is not registered.");
    }
}

public class Problem1CodeSprintJudgingDesk {
    public static void main(String[] args) {
        Hackathon8A hackathon = new Hackathon8A("Code Sprint");

        Student8A asha = new Student8A("S1", "Asha");
        Student8A ravi = new Student8A("S2", "Ravi");
        Student8A neha = new Student8A("S3", "Neha");
        Student8A kiran = new Student8A("S4", "Kiran");

        Team8A byteBusters = new Team8A(
                "ByteBusters", Arrays.asList(asha, ravi, neha), new InnovationScoringRule8A());

        try {
            hackathon.registerTeam(new Team8A(
                    "SoloCoder", Collections.singletonList(kiran), new OpenScoringRule8A()));
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }

        hackathon.registerTeam(byteBusters);
        Project8A project = new Project8A("SmartAttend");
        hackathon.submitProject(byteBusters, project);

        hackathon.startJudging();
        Judge8A judge = new Judge8A("Judge 1");
        Score8A score = new Score8A(judge, project, 8, 7, 9);
        hackathon.recordScore(score);

        System.out.printf("Final score: %.2f%n",
                score.finalScore(byteBusters.getScoringRule()));

        hackathon.publishResults();

        try {
            Score8A changedScore = new Score8A(judge, project, 10, 7, 9);
            hackathon.rescore(changedScore);
        } catch (IllegalStateException e) {
            System.out.println("Rescore rejected: " + e.getMessage());
        }
    }
}
