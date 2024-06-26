package ui;

import controller.authorization.AuthenticationController;
import model.*;
import model.repository.*;
import model.repository.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static model.GardenType.GARDEN;


public class Bootstrap implements Runnable {

    //Add some task categories to the repository as bootstrap
    public void run() {
        addUsers();
        addOrganization();
        addTaskCategories();
        CreateSkills();
        createJobs();
        createCollaborators();
        createGreenSpaces();
        createEntries();
    }



    private void createEntries() {
        EntryRepository entryRepository=Repositories.getInstance().getEntryRepository();
        GreenSpaceRepository greenSpaceRepository = Repositories.getInstance().getGreenSpaceRepository();
        GreenSpace g=greenSpaceRepository.getGreenSpaceByName("Garden","gsm@this.app");
        greenSpaceRepository.registerGreenSpace("Medium Garden","Garden",47,"Rua","gsm@this.app");
        entryRepository.addEntryToDoList(new Task("clean","clean all park"), Duration.parse("P4DT2H"), "Low" ,g,"gsm@this.app");
        entryRepository.addEntryToDoList(new Task("clean","clean"), Duration.parse("P42DT3H"), "Medium" ,g,"gsm@this.app");
        entryRepository.addEntryToDoList(new Task("clean Park","clean all park"), Duration.parse("P2DT6H"), "High" ,g,"gsm@this.app");
        entryRepository.addEntryToDoList(new Task("Cut trees","cut all trees"), Duration.parse("P3DT5H"), "High" ,g,"gsm@this.app");
        entryRepository.addEntryToDoList(new Task("Cut grass","cut all grass"), Duration.parse("P0DT4H"), "Low" ,g,"gsm@this.app");
    }

    private void createGreenSpaces() {
        GreenSpaceRepository greenSpaceRepository = Repositories.getInstance().getGreenSpaceRepository();
        greenSpaceRepository.registerGreenSpace("Garden","Garden",34,"Rua","gsm@this.app");
        greenSpaceRepository.registerGreenSpace("Park","Medium Sized Park",59,"Via","gsm@this.app");
        greenSpaceRepository.registerGreenSpace("Jardim","Large Sized Park",125,"Route","gsm@this.app");
        greenSpaceRepository.registerGreenSpace("Small Garden","Garden",14,"Street","gsm@this.app");
    }

    private void addOrganization() {
        //TODO: add organizations bootstrap here
        //get organization repository
        OrganizationRepository organizationRepository = Repositories.getInstance().getOrganizationRepository();
        Organization organization = new Organization("This Company");
        organization.addEmployee(new Employee("admin@this.app"));
        organization.addEmployee(new Employee("employee@this.app"));
        organization.addEmployee(new Employee("hrm@this.app"));
        organization.addEmployee(new Employee("gsm@this.app"));
        organizationRepository.add(organization);
    }

    private void addTaskCategories() {
        //TODO: add bootstrap Task Categories here

        //get task category repository
 /*       TaskRepository taskCategoryRepository = Repositories.getInstance().getTaskCategoryRepository();
        taskCategoryRepository.add(new TaskCategory("Analysis"));
        taskCategoryRepository.add(new TaskCategory("Design"));
        taskCategoryRepository.add(new TaskCategory("Implementation"));
        taskCategoryRepository.add(new TaskCategory("Development"));
        taskCategoryRepository.add(new TaskCategory("Testing"));
        taskCategoryRepository.add(new TaskCategory("Deployment"));
        taskCategoryRepository.add(new TaskCategory("Maintenance"));
   */ }

    private void addUsers() {
        //TODO: add Authentication users here: should be created for each user in the organization
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        authenticationRepository.addUserRole(AuthenticationController.ROLE_ADMIN,
                AuthenticationController.ROLE_ADMIN);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_HRM,
                AuthenticationController.ROLE_HRM);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_EMPLOYEE,
                AuthenticationController.ROLE_EMPLOYEE);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_GSM,
                AuthenticationController.ROLE_GSM);

        authenticationRepository.addUserWithRole("Main Administrator", "admin@this.app", "admin",
                AuthenticationController.ROLE_ADMIN);

        authenticationRepository.addUserWithRole("Employee", "john.doe@example.com", "pwd",
                AuthenticationController.ROLE_EMPLOYEE);

        authenticationRepository.addUserWithRole("Human Resources Manager", "hrm@this.app", "hrm",
                AuthenticationController.ROLE_HRM);

        authenticationRepository.addUserWithRole("Green Space Manager", "gsm@this.app", "gsm",
                AuthenticationController.ROLE_GSM);
    }

    private void CreateSkills(){
        SkillRepository skillRepository = Repositories.getInstance().getSkillRepository();
        // List of random skill names to add
        String[] skillNames = {
                "Java Programming",
                "Advanced Excel",
                "Project Management",
                "Digital Marketing",
                "Machine Learning",
                "Public Speaking",
                "Web Development",
                "Graphic Design",
                "Data Analysis",
                "Creative Writing",
                "Drivers License category B",
                "Drivers License category C",
                "Drivers License category B",
                "Cleaning expertise",
                "Cleaning"
        };

        // Add each skill in the list to the repository
        for (String skillName : skillNames) {
            Optional<Skill> addedSkill = skillRepository.addSkillByName(skillName.toLowerCase());
            if (addedSkill.isPresent()) {
                System.out.println("Skill added successfully: " + addedSkill.get().getName());
            } else {
                System.out.println("Failed to add skill '" + skillName + "'. It may already exist or input was invalid.");
            }
        }
    }

    private void createJobs() {
        JobRepository jobRepository = Repositories.getInstance().getJobRepository();
        String[] jobNames = {
                "Software Developer",
                "Data Scientist",
                "Project Manager",
                "Graphic Designer",
                "Marketing Specialist",
                "Cleaning"
        };

        for (String jobName : jobNames) {
            boolean added = true;

            jobRepository.addJobByName(jobName);

            if (added) {
                System.out.println("Job added successfully: " + jobName);
            } else {
                System.out.println("Failed to add job '" + jobName + "'. It may already exist or input was invalid.");
            }
        }
    }

    private void createCollaborators() {
        CollaboratorRepository collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
        SkillRepository skillRepository = Repositories.getInstance().getSkillRepository();
        JobRepository jobRepository = Repositories.getInstance().getJobRepository();
        Collaborator c=new Collaborator("John Doe", new Date(), new Date(), "123 Main St", "CityA", "12345678", 912345678, "john.doe@example.com", "ID Card", "12345678-1JD1", jobRepository.getJobByName("Cleaning"));
        collaboratorRepository.registerCollaborator("John Doe", new Date(), new Date(), "123 Main St", "CityA", "12345678", 912345678, "john.doe@example.com", "ID Card", "12345678-1JD1", jobRepository.getJobByName("Cleaning"));

        collaboratorRepository.registerCollaborator("Jane Smith", new Date(), new Date(), "456 Elm St", "CityB", "54321678", 912165487, "jane.smith@example.com", "ID Card", "12345678-1JS1", jobRepository.getJobByName("Data Scientist"));


        collaboratorRepository.registerCollaborator("Alice Johnson", new Date(), new Date(), "789 Oak St", "CityC", "67890678", 913245687, "alice.johnson@example.com", "ID Card", "12345678-1AJ1", jobRepository.getJobByName("Project Manager"));


        collaboratorRepository.registerCollaborator("Bob Brown", new Date(), new Date(), "321 Pine St", "CityD", "98765678", 913245687, "bob.brown@example.com", "ID Card", "12345678-1BB1", jobRepository.getJobByName("Graphic Designer"));


        collaboratorRepository.registerCollaborator("Carol White", new Date(), new Date(), "654 Cedar St", "CityE", "87654678", 913245687, "carol.white@example.com", "ID Card", "12345678-1CW1", jobRepository.getJobByName("Marketing Specialist"));

        collaboratorRepository.addSkills(skillRepository.getSkills(), "12345678-1JD1");
        TeamRepository teamRepository=Repositories.getInstance().getTeamRepository();
        List<Collaborator> c1=new ArrayList<Collaborator>();
        c1.add(c);
        teamRepository.addTeam(new Team("Tem one", c1));
        teamRepository.addTeam(new Team("Tem two", c1));
        teamRepository.addTeam(new Team("Tem three", c1));
        teamRepository.addTeam(new Team("Tem four", c1));

    }
}