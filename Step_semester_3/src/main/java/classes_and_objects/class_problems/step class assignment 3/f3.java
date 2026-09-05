class HostelRoom {

    String roomNo;
    int beds;
    int occupied;
    void allot(String studentName) {

        if (occupied < beds) {
            occupied++;
            System.out.println(studentName + " allotted to room " + roomNo);
        } else {
            System.out.println("Room " + roomNo + " is full. " +
                               studentName + " added to waiting list.");
        }
    }
}

public class f3 {

    public static void main(String[] args) {
        HostelRoom room214 = new HostelRoom();

        room214.roomNo = "C-214";
        room214.beds = 2;
        room214.occupied = 0;

        HostelRoom sameRoom = room214;

        sameRoom.allot("Ravi");

        System.out.println("room214 occupied (seen via first variable): "
                           + room214.occupied);

        System.out.println("sameRoom == room214: " + (sameRoom == room214));

        HostelRoom separate = new HostelRoom();

        separate.roomNo = "C-214";
        separate.beds = 2;
        separate.occupied = 1;

        System.out.println("separate == room214: " + (separate == room214));

        room214.allot("Anitha");
        room214.allot("Karthik");
    }
}