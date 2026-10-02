package inheritance_polymorphism.class_problems;

import java.util.*;

abstract class Question8C {
    private final String questionId;
    private final String text;
    protected final String correctAnswer;

    public Question8C(String questionId, String text, String correctAnswer) {
        this.questionId = questionId;
        this.text = text;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionId() { return questionId; }
    public String getText() { return text; }
    public abstract boolean isCorrect(String answer);
}

class MultipleChoiceQuestion8C extends Question8C {
    private final Set<String> options;

    public MultipleChoiceQuestion8C(String questionId, String text, String correctAnswer,
                                    String... options) {
        super(questionId, text, correctAnswer);
        this.options = new LinkedHashSet<>(Arrays.asList(options));
    }

    @Override
    public boolean isCorrect(String answer) {
        return options.contains(answer) && correctAnswer.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion8C extends Question8C {
    public TrueFalseQuestion8C(String questionId, String text, boolean correctAnswer) {
        super(questionId, text, Boolean.toString(correctAnswer));
    }

    @Override
    public boolean isCorrect(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class Student8C {
    private final String studentId;
    private final String name;

    public Student8C(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }
}

class Attempt8C {
    private final Student8C student;
    private final Examination8C examination;
    private final Map<String, String> answers = new LinkedHashMap<>();
    private boolean started;
    private boolean submitted;
    private int score = -1;

    public Attempt8C(Student8C student, Examination8C examination) {
        this.student = student;
        this.examination = examination;
    }

    public void start() {
        if (submitted) throw new IllegalStateException("Submitted attempt cannot be restarted.");
        started = true;
        System.out.println("Examination '" + examination.getTitle() + "' started by " + student.getName() + ".");
    }

    public void answerQuestion(String questionId, String answer) {
        if (!started) throw new IllegalStateException("Attempt has not been started.");
        if (submitted) throw new IllegalStateException("Answers cannot be changed after submission.");
        if (!examination.hasQuestion(questionId)) throw new IllegalArgumentException("Unknown question.");
        answers.put(questionId, answer);
        System.out.println("Question " + questionId + " answered with '" + answer + "'.");
    }

    public void submit() {
        if (!started) throw new IllegalStateException("Attempt has not been started.");
        if (submitted) throw new IllegalStateException("Attempt already submitted.");
        submitted = true;
        score = examination.evaluate(this);
        System.out.println("Examination '" + examination.getTitle() + "' submitted successfully.");
        System.out.println("Result for '" + examination.getTitle() + "' attempt: "
                + score + "/" + examination.getQuestions().size() + " correct.");
    }

    public String getAnswer(String questionId) { return answers.get(questionId); }
    public boolean isSubmitted() { return submitted; }
    public Student8C getStudent() { return student; }
}

class Examination8C {
    private final String title;
    private final List<Question8C> questions = new ArrayList<>();
    private final Map<String, Attempt8C> submittedAttempts = new HashMap<>();

    public Examination8C(String title) {
        this.title = title;
    }

    public String getTitle() { return title; }
    public List<Question8C> getQuestions() { return Collections.unmodifiableList(questions); }

    public void addQuestion(Question8C question) {
        questions.add(question);
    }

    public boolean hasQuestion(String questionId) {
        return questions.stream().anyMatch(q -> q.getQuestionId().equals(questionId));
    }

    public Attempt8C startAttempt(Student8C student) {
        Attempt8C previous = submittedAttempts.get(student.getStudentId());
        if (previous != null) {
            throw new IllegalStateException("Student already has a submitted attempt.");
        }
        return new Attempt8C(student, this);
    }

    public int evaluate(Attempt8C attempt) {
        int correct = 0;
        for (Question8C question : questions) {
            String answer = attempt.getAnswer(question.getQuestionId());
            if (answer != null && question.isCorrect(answer)) {
                correct++;
            }
        }
        submittedAttempts.putIfAbsent(attempt.getStudent().getStudentId(), attempt);
        return correct;
    }
}

public class Problem1OnlineExaminationSystem {
    public static void main(String[] args) {
        Student8C student = new Student8C("S101", "Priyanshu");
        Examination8C exam = new Examination8C("Math Quiz");

        exam.addQuestion(new MultipleChoiceQuestion8C(
                "1", "2 + 2 = ?", "A", "A", "B", "C", "D"));
        exam.addQuestion(new MultipleChoiceQuestion8C(
                "2", "3 + 3 = ?", "B", "A", "B", "C", "D"));

        Attempt8C attempt = exam.startAttempt(student);
        attempt.start();
        attempt.answerQuestion("1", "A");
        attempt.answerQuestion("2", "C");
        attempt.submit();

        try {
            attempt.answerQuestion("2", "B");
        } catch (IllegalStateException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
    }
}
