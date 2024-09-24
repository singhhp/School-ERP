public class Student {
    private int id;
    private String name;
    private String className;
    private String phoneNumber;

    // Constructor
    public Student(int id, String name, String className, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.phoneNumber = phoneNumber;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id; // You might want to add validation here
    }

    public void setName(String name) {
        this.name = name; // You can add validation to ensure name is not empty
    }

    public void setClassName(String className) {
        this.className = className; // Add validation if needed
    }

    public void setPhoneNumber(String phoneNumber) {
        // Basic validation for phone number length (you can customize as needed)
        if (phoneNumber != null && phoneNumber.length() == 10) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Phone number must be 10 digits long");
        }
    }
}
