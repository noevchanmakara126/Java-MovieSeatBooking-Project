
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class Miniproject {
    static int count = 0;
    static final int max = 100;
    static String[][] bseat = new String[max][];
    static int[] lastid = new int[max];
    static int[] lastbook = new int[max];

    public static void main(String[] args) {
        int booked = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Set Up Row Seat : ");
        Integer r = scanner.nextInt();
        System.out.printf("Set Up Seat per Row : ");
        Integer c = scanner.nextInt();
        String[][] mh = new String[r][c];
        String[][] ah = new String[r][c];
        String[][] eh = new String[r][c];
        for (int i = 0; i < r; i++ ) {
            for (int j = 0; j < c; j++){
                mh[i][j] = "AL";
                ah[i][j] = "AL";
                eh[i][j] = "AL";
            }
        }

        do {
            Miniproject.Menu();
            System.out.printf("Select Your Option : ");
            Integer op = scanner.nextInt();
            switch (op) {
                case 1 -> booking(mh, ah, eh);
                case 2 -> {
                    showhall(mh, ah, eh);
                }
                case 3 -> showtime();
                case 5 -> rebootHall(mh,ah,eh);
                case 4 -> History();
                case 6 -> {
                    System.out.println("Thanks You!");
                    System.exit(0);
                }
                default -> {
                    System.out.println("Please Enter Option by (1-6)");
                }
            }
        } while (true);
    }

    public static void Menu() {
        System.out.println("++++++++++++++++++++++++++++++");
        System.out.println("+++++++[Apllicaton Manu]++++++");
        System.out.println("++++++++++++++++++++++++++++++");
        System.out.println("1.Booking");
        System.out.println("2.Hall");
        System.out.println("3.Showtime");
        System.out.println("4.History");
        System.out.println("5.Reboot-Showtime");
        System.out.println("6.Exit");
        System.out.println("++++++++++++++++++++++++++++++");
    }

    public static void hall(String[][] halls) {
        for (int i = 0; i < halls.length; ++i) {
            for (int j = 0; j < halls[i].length; ++j) {

                System.out.printf("[%c-%d::" + (halls[i][j].equals("BO") ? "BO" : "AV") + "] ", 'A' + i, j+1 );
            }
            System.out.println();
        }
    }
    public static void setuphall(String[][] mh, String[][] ah, String[][] eh) {
    }
    public static void showhall(String[][] mh, String[][] ah, String[][] eh) {
        System.out.println("[\t Morning \t]");
        hall(mh);
        System.out.println("======================");
        System.out.println("[\t Afternoon \t]");
        hall(ah);
        System.out.println("======================");
        System.out.println("[\t Evening \t]");
        hall(eh);

    }
    public static void rebootHall(String[][] mh, String[][] ah, String[][] eh) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Are you sure you want to reboot the hall? (Y/N) : ");
        String confirmation = scanner.nextLine().toUpperCase();
        if ("Y".equals(confirmation)) {
            for (int i = 0; i < mh.length; i++) {
                for (int j = 0; j < mh[i].length; j++) {
                    mh[i][j] = "AV";
                    ah[i][j] = "AV";
                    eh[i][j] = "AV";
                }
            }

            count = 0;

            Arrays.fill(lastid, 0, lastid.length, 0);
            Arrays.fill(lastbook, 0, lastbook.length, 0);
            for (int i = 0; i < bseat.length; i++) {
                bseat[i] = null;
            }
            System.out.println("Successfully Rebooted and History Cleared!");
        } else {
            System.out.println("Reboot cancelled.");
        }
    }

    public static void showtime() {
        System.out.println("===========================");
        System.out.println("1.Morning [10:00AM-1:00PM]");
        System.out.println("2.Afternoon [2:00PM-5:00PM]");
        System.out.println("3.Evening [6:00PM-10:00PM]");
        System.out.println("===========================");
    }

    public static void booking(String[][] mh, String[][] ah, String[][] eh) {
        setuphall(mh, ah, eh);
        int ip = 0;
        Boolean right;
        Scanner scanner = new Scanner(System.in);
        showtime();
        do {
            System.out.printf("Which ShowTime Do you Prefer : ");
            String op = scanner.nextLine();
            if (op.matches("^[1-3]$")) {
                ip = Integer.parseInt(op);
                right = true;
            } else {
                System.out.println("Please input the valid option from 1-3!!");
                right = false;
            }
        } while (!right);
        String[][] option = new String[0][];
        switch (ip) {
            case 1:
                option = mh;
                break;
            case 2:
                option = ah;
                break;
            case 3:
                option = eh;
                break;
            default:
                System.out.println("Please Choose Again!!!!.");
        }
        System.out.println("Available seats in the " + (ip == 1 ? "Morning" : ip == 2 ? "Afternoon" : "Evening") + " Halls  :");
        hall(option);
        System.out.println("-------Booking Guide--------- ");
        System.out.println("Single Booking by : [A-1] or [a-1] ");
        System.out.println("Multi Booking by[A-1,A-2] or[a-1,a-2] ");
        String seatinput;
        do {
            System.out.print("Please Enter The Seat Number : ");
            seatinput = scanner.next().toUpperCase();
            if (seatinput.matches("^([A-C]-\\d+(,[A-C]-\\d+)*)?$")) {
                String[] snum = seatinput.split(",");
                boolean allSeatsAvailable = true;
                for (String seatNumber : snum) {
                    String[] parts = seatNumber.split("-");
                    int row = parts[0].charAt(0) - 'A';
                    int col = Integer.parseInt(parts[1]) - 1;
                    if (option[row][col].equals("BO")) {
                        System.out.println("This seat is already booked. Please choose another seat.");
                        allSeatsAvailable = false;
                        break;
                    }
                }
                if (allSeatsAvailable) {
                    for (String seatNumber : snum) {
                        String[] parts = seatNumber.split("-");
                        int row = parts[0].charAt(0) - 'A';
                        int col = Integer.parseInt(parts[1]) - 1;
                        option[row][col] = "BO"; // Mark the seat as booked
                    }
                    right = true;
                } else {
                    right = false;
                }
            } else {
                System.out.println("-------Booking Guide--------- ");
                System.out.println("Single Booking by : [A-1] or [a-1] ");
                System.out.println("Multi Booking by[A-1,A-2] or[a-1,a-2] ");
                right = false;
            }
        } while (!right);
        String[] snum = seatinput.split(",");
        System.out.printf("Enter Your Student ID : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.printf("Are You Sure [Y/N]: ");
        String yn = scanner.next();
        if ("Y".equalsIgnoreCase(yn)) {
            for (String seatNumber : snum) {
                String[] parts = seatNumber.split("-");
                int row = parts[0].charAt(0) - 'A';
                int col = Integer.parseInt(parts[1]) - 1;
                option[row][col] = "BO";
            }
            System.out.println("Booking Successful!");
            count++;

            lastid[count - 1] = id;
            lastbook[count - 1] = ip;
            bseat[count - 1] = snum;
        } else {
            System.out.println("Booking Canceled.");
        }
    }

    public static void History() {
        if(count != 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDateTime = LocalDateTime.now().plusHours(0).format(formatter);

            System.out.println("ShowTime\t\tID\t\tSeat\t\t\tDate And BookedTime");

            for (int i = 0; i < count; i++) {
                String time = lastbook[i] == 1 ? "Morning" : lastbook[i] == 2 ? "Afternoon" : "Evening";
                String seats = Arrays.toString(bseat[i]).replaceAll("\\[|]", "").replace(", ", ",");
                System.out.println(String.format("%-15s%-10s%-15s%s", time, lastid[i], seats, formattedDateTime));
            }
        } else {
            System.out.println("=========================================");
            System.out.println("You don't have any booking yet!!");
            System.out.println("=========================================");
        }
    }
}
