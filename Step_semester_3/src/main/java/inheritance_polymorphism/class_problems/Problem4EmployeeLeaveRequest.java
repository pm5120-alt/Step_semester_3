package inheritance_polymorphism.class_problems;

import java.time.LocalDate;

interface LeavePolicy8C {
    boolean canRequestLeave(LocalDate start, LocalDate end);
    String getPolicyName();
}

class FullTimeLeavePolicy8C implements LeavePolicy8C {
    public boolean canRequestLeave(LocalDate start, LocalDate end) {
        return !end.isBefore(start);
    }
    public String getPolicyName() { return "Full-time"; }
}

class PartTimeLeavePolicy8C implements LeavePolicy8C {
    public boolean canRequestLeave(LocalDate start, LocalDate end) {
        return !end.isBefore(start);
    }
    public String getPolicyName() { return "Part-time"; }
}

class ContractLeavePolicy8C implements LeavePolicy8C {
    public boolean canRequestLeave(LocalDate start, LocalDate end) {
        return !end.isBefore(start);
    }
    public String getPolicyName() { return "Contract"; }
}

class Employee8C {
    private final String employeeId;
    private final String name;
    private final LeavePolicy8C policy;

    public Employee8C(String employeeId, String name, LeavePolicy8C policy) {
        this.employeeId = employeeId;
        this.name = name;
        this.policy = policy;
    }

    public String getName() { return name; }
    public LeavePolicy8C getPolicy() { return policy; }
}

class LeaveRequest8C {
    enum Status { PENDING, APPROVED, REJECTED }

    private final Employee8C employee;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private Status status = Status.PENDING;

    public LeaveRequest8C(Employee8C employee, LocalDate startDate, LocalDate endDate) {
        if (!employee.getPolicy().canRequestLeave(startDate, endDate)) {
            throw new IllegalArgumentException("Leave request violates employee policy.");
        }
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void approve() { changeStatus(Status.APPROVED); }
    public void reject() { changeStatus(Status.REJECTED); }

    private void changeStatus(Status newStatus) {
        if (status != Status.PENDING) {
            throw new IllegalStateException(
                    "Cannot change status: " + formatStatus(status)
                    + " request cannot revert to Pending.");
        }
        status = newStatus;
    }

    private String formatStatus(Status value) {
        String raw = value.toString().toLowerCase();
        return raw.substring(0, 1).toUpperCase() + raw.substring(1);
    }

    public Employee8C getEmployee() { return employee; }
    public Status getStatus() { return status; }
}

class LeaveApprovalService8C {
    public LeaveRequest8C submit(Employee8C employee, LocalDate start, LocalDate end) {
        LeaveRequest8C request = new LeaveRequest8C(employee, start, end);
        System.out.println("Leave request submitted by " + employee.getName()
                + " for " + start + " to " + end + ". Status: Pending.");
        return request;
    }

    public void approve(LeaveRequest8C request) {
        request.approve();
        System.out.println("Leave request for " + request.getEmployee().getName()
                + " approved. Status: Approved.");
    }

    public void reject(LeaveRequest8C request) {
        request.reject();
        System.out.println("Leave request for " + request.getEmployee().getName()
                + " rejected. Status: Rejected.");
    }
}

public class Problem4EmployeeLeaveRequest {
    public static void main(String[] args) {
        LeaveApprovalService8C service = new LeaveApprovalService8C();

        Employee8C john = new Employee8C("E1", "John Doe", new FullTimeLeavePolicy8C());
        LeaveRequest8C johnRequest = service.submit(
                john, LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 12));
        service.approve(johnRequest);

        Employee8C jane = new Employee8C("E2", "Jane Smith", new PartTimeLeavePolicy8C());
        service.submit(jane, LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 5));

        try {
            johnRequest.approve();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
