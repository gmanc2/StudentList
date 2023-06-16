package student_list;
import javax.swing.JOptionPane;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class student_list {
    public static void main(String[] args) {
        student_data data = new student_data();
        data.readdata();
        data.printdata();
        data.printstats();
        System.exit(0);
    }
}

class student_data extends student_node
{
    student_node first, current, previous;
    student_data()
    {
        //make a dummy first link
        first = new student_node();
        first.next = null;
        previous = first;
    }
    void readdata() {
        String input;
        do {
            int id;
            do {
                try {
                    id = Integer.parseInt(JOptionPane.showInputDialog("Enter Student ID: "));
                    if (id <= 0) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a positive integer for the student ID.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number for Student ID.");
                    id = -1;
                }
            } while (id <= 0);

            String name;
            Pattern validPattern = Pattern.compile("^[a-zA-Z ]+$");
            do {
                name = JOptionPane.showInputDialog("Enter Student Name: ").trim();
                Matcher nameMatcher = validPattern.matcher(name);
                if (!nameMatcher.matches()) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a name containing only letters (A-Z) and a space between the Firstname and Lastname.");
                    name = "";
                } else if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a full Firstname and Lastname.");
                }
            } while (name.isEmpty());

            String major;
            do {
                major = JOptionPane.showInputDialog("Enter Major (CIS or Math): ");
                if (!major.equalsIgnoreCase("CIS") && !major.equalsIgnoreCase("Math")) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter CIS or Math.");
                }
            } while (!major.equalsIgnoreCase("CIS") && !major.equalsIgnoreCase("Math"));

            double gpa;
            do {
                try {
                    gpa = Double.parseDouble(JOptionPane.showInputDialog("Enter Student GPA: "));
                    if (gpa < 0.0 || gpa > 4.0) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a GPA between 0.0 and 4.0.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number 0.0 to 4.0 .");
                    gpa = -1;
                }
            } while (gpa < 0.0 || gpa > 4.0);

            student_node newNode = new student_node();
            newNode.stu_number = id;
            newNode.stu_name = name;
            newNode.major = major;
            newNode.gpa = gpa;
            newNode.next = null;
            previous.next = newNode;
            previous = newNode;

            do {
                input = JOptionPane.showInputDialog("Do you want to enter another student? (yes/no)");
                if (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no")) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter yes or no.");
                }
            } while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no"));

        } while (input.equalsIgnoreCase("yes"));
    }

    void printdata()
    {
        current = first.next;
        while (current != null) {
            System.out.println("ID: " + current.stu_number + ", Name: " + current.stu_name + ", Major: " + current.major + ", GPA: " + current.gpa);
            current = current.next;
        }
    }

    void printstats()
    {
        System.out.println("\nCIS Students:");
        current = first.next;
        while (current != null) {
            if (current.major.equalsIgnoreCase("CIS")) {
                System.out.println("ID: " + current.stu_number + ", Name: " + current.stu_name);
            }
            current = current.next;
        }

        System.out.println("\nMath Students:");
        current = first.next;
        while (current != null) {
            if (current.major.equalsIgnoreCase("Math")) {
                System.out.println("ID: " + current.stu_number + ", Name: " + current.stu_name);
            }
            current = current.next;
        }

        System.out.println("\nHonor Students (GPA 3.5 or greater):");
        current = first.next;
        while (current != null) {
            if (current.gpa >= 3.5) {
                System.out.println("Name: " + current.stu_name + ", GPA: " + current.gpa);
            }
            current = current.next;
        }

        student_node highestGPA = null;
        current = first.next;
        while (current != null) {
            if (current.major.equalsIgnoreCase("CIS")) {
                if (highestGPA == null || current.gpa > highestGPA.gpa) {
                    highestGPA = current;
                }
            }
            current = current.next;
        }

        if (highestGPA != null) {
            System.out.println("\nCIS Student with the highest GPA:");
            System.out.println("ID: " + highestGPA.stu_number + ", Name: " + highestGPA.stu_name + ", Major: " + highestGPA.major + ", GPA: " + highestGPA.gpa);
        } else {
            System.out.println("\nNo CIS students found.");
        }
    }
}

class student_node
{
    int stu_number;
    String stu_name;
    String major;
    double gpa;
    student_node next;
}