//Manager class
class Manager implements Employee {
    private String name;
    Manager(String name) { this.name = name; }

    public String getName() { return name; }
    public void work() { System.out.println(name + " is planning projects..."); }
    public String getRole() { return "Manager"; }
}