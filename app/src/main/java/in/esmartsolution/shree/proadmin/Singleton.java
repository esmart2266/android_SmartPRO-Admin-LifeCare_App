package in.esmartsolution.shree.proadmin;

import in.esmartsolution.shree.proadmin.model.VisitMain;

public class Singleton {
    private static Singleton uniqInstance;
    public VisitMain visitMain = new VisitMain();

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (uniqInstance == null)
            uniqInstance = new Singleton();
        return uniqInstance;
    }

    public void setVisitMain(VisitMain visitMain) {
        this.visitMain = visitMain;

    }

    public VisitMain getVisitMain() {
        return this.visitMain;

    }
} 