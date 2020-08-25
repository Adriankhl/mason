package sim.app.tutorial;

import sim.engine.*;
import sim.util.*;
import sim.field.continuous.*;
import sim.field.network.*;

public class Students extends SimState {
    public Continuous2D yard = new Continuous2D(1.0,100,100);
    public int numStudents = 50;
    double forceToSchoolMultiplier = 0.01;
    double randomMultiplier = 0.1;
    public Network buddies = new Network(false);

    public int getNumStudents() { return numStudents; }
    public void setNumStudents(int val) { if (val > 0) numStudents = val; }
    public double getForceToSchoolMultiplier() { return forceToSchoolMultiplier; }
    public void setForceToSchoolMultiplier(double val)
    { if (forceToSchoolMultiplier >= 0.0) forceToSchoolMultiplier = val; }
    public double getRandomMultiplier() { return randomMultiplier; }
    public void setRandomMultiplier(double val) { if (randomMultiplier >= 0.0) randomMultiplier = val; }
    public Object domRandomMultiplier() { return new sim.util.Interval(0.0, 100.0); }
    public double[] getAgitationDistribution()
    {
        Bag students = buddies.getAllNodes();
        double[] distro = new double[students.numObjs];
        for(int i = 0; i < students.numObjs; i++)
            distro[i] = ((Student)(students.objs[i])).getAgitation();
        return distro;
    }

    public Students(long seed)
    {
        super(seed);
    }

    @Override
    public void start()
    {
        super.start();
        // clear the yard
        yard.clear();
        // clear the buddies
        buddies.clear();
        // add some students to the yard
        for(int i = 0; i < numStudents; i++)
        {
            Student student = new Student();
            yard.setObjectLocation(student,
                    new Double2D(yard.getWidth() * 0.5 + random.nextDouble() - 0.5,
                            yard.getHeight() * 0.5 + random.nextDouble() - 0.5));

            buddies.addNode(student);

            schedule.scheduleRepeating(student);
        }

        // define like/dislike relationships
        Bag students = buddies.getAllNodes();
        for(int i = 0; i < students.size(); i++)
        {
            Object student = students.get(i);
// who does he like?
            Object studentB = null;
            do
                studentB = students.get(random.nextInt(students.numObjs));
            while (student == studentB);
            double buddiness = random.nextDouble();
            buddies.addEdge(student, studentB, new Double(buddiness));
// who does he dislike?
            do
                studentB = students.get(random.nextInt(students.numObjs));
            while (student == studentB);
            buddiness = random.nextDouble();
            buddies.addEdge(student, studentB, new Double( -buddiness));
        }
    }



    public static void main(String[] args)
    {
        doLoop(Students.class, args);
        System.exit(0);
    }
}
