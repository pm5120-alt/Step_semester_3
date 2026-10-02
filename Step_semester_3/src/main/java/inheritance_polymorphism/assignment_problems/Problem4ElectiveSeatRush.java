package inheritance_polymorphism.assignment_problems;

import java.util.*;

interface CreditPolicy8A {
    int getCreditLimit();
    String getName();
}

class RegularCreditPolicy8A implements CreditPolicy8A {
    public int getCreditLimit() { return 24; }
    public String getName() { return "Regular"; }
}

class HonorsCreditPolicy8A implements CreditPolicy8A {
    public int getCreditLimit() { return 28; }
    public String getName() { return "Honors"; }
}

class ExchangeCreditPolicy8A implements CreditPolicy8A {
    public int getCreditLimit() { return 20; }
    public String getName() { return "Exchange"; }
}

class Student8AElective {
    private final String studentId;
    private final String name;
    private final CreditPolicy8A policy;
    private int currentCredits;

    public Student8AElective(String studentId, String name,
                             CreditPolicy8A policy, int currentCredits) {
        this.studentId = studentId;
        this.name = name;
        this.policy = policy;
        this.currentCredits = currentCredits;
    }

    public boolean canAddCredits(int credits) {
        return currentCredits + credits <= policy.getCreditLimit();
    }

    public void addCredits(int credits) { currentCredits += credits; }
    public void removeCredits(int credits) { currentCredits -= credits; }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public int getCurrentCredits() { return currentCredits; }
    public CreditPolicy8A getPolicy() { return policy; }
}

class Elective8A {
    private final String name;
    private final int credits;
    private final int capacity;
    private final List<Student8AElective> enrolled = new ArrayList<>();
    private final Queue<Student8AElective> waitlist = new ArrayDeque<>();

    public Elective8A(String name, int credits, int capacity) {
        if (credits <= 0 || capacity <= 0) {
            throw new IllegalArgumentException("Credits and capacity must be positive.");
        }
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
    }

    public void enroll(Student8AElective student) {
        if (isAlreadyEnrolledOrWaiting(student)) {
            throw new IllegalStateException(student.getName()
                    + " is already enrolled or waitlisted.");
        }

        // Credit limit is checked before seat availability as required.
        if (!student.canAddCredits(credits)) {
            throw new IllegalStateException(student.getName()
                    + " would exceed the " + student.getPolicy().getName()
                    + " credit limit (" + (student.getCurrentCredits() + credits)
                    + "/" + student.getPolicy().getCreditLimit() + ").");
        }

        if (enrolled.size() < capacity) {
            enrolled.add(student);
            student.addCredits(credits);
            printEnrolled(student);
        } else {
            waitlist.offer(student);
            System.out.println(name + " is full.");
            System.out.println(student.getName() + " added to waitlist (position "
                    + waitlist.size() + ").");
        }
    }

    public void drop(Student8AElective student) {
        if (!enrolled.remove(student)) {
            throw new IllegalStateException(student.getName() + " is not enrolled.");
        }

        student.removeCredits(credits);
        System.out.println(student.getName() + " dropped " + name
                + " (credits: " + student.getCurrentCredits()
                + "/" + student.getPolicy().getCreditLimit() + ").");

        promoteFirstEligible();
    }

    private void promoteFirstEligible() {
        Iterator<Student8AElective> iterator = waitlist.iterator();

        while (iterator.hasNext() && enrolled.size() < capacity) {
            Student8AElective candidate = iterator.next();

            if (!candidate.canAddCredits(credits)) {
                iterator.remove();
                System.out.println(candidate.getName()
                        + " removed from waitlist: credit limit no longer allows enrollment.");
                continue;
            }

            iterator.remove();
            enrolled.add(candidate);
            candidate.addCredits(credits);
            System.out.println(candidate.getName()
                    + " promoted from waitlist and enrolled in " + name
                    + " (credits: " + candidate.getCurrentCredits()
                    + "/" + candidate.getPolicy().getCreditLimit() + ").");
        }
    }

    private void printEnrolled(Student8AElective student) {
        System.out.println(student.getName() + " enrolled in " + name
                + " (credits: " + student.getCurrentCredits()
                + "/" + student.getPolicy().getCreditLimit() + ").");
    }

    private boolean isAlreadyEnrolledOrWaiting(Student8AElective student) {
        return enrolled.stream().anyMatch(
                    s -> s.getStudentId().equals(student.getStudentId()))
                || waitlist.stream().anyMatch(
                    s -> s.getStudentId().equals(student.getStudentId()));
    }
}

public class Problem4ElectiveSeatRush {
    public static void main(String[] args) {
        Elective8A cloud = new Elective8A("Cloud Computing", 4, 2);

        Student8AElective asha =
                new Student8AElective("S1", "Asha", new RegularCreditPolicy8A(), 20);
        Student8AElective ravi =
                new Student8AElective("S2", "Ravi", new HonorsCreditPolicy8A(), 22);
        Student8AElective neha =
                new Student8AElective("S3", "Neha", new ExchangeCreditPolicy8A(), 12);
        Student8AElective kiran =
                new Student8AElective("S4", "Kiran", new RegularCreditPolicy8A(), 22);

        cloud.enroll(asha);
        cloud.enroll(ravi);
        cloud.enroll(neha);

        try {
            cloud.enroll(kiran);
        } catch (IllegalStateException e) {
            System.out.println("Enrollment failed: " + e.getMessage());
        }

        cloud.drop(asha);
    }
}
