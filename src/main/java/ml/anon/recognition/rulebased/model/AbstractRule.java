package ml.anon.recognition.rulebased.model;

/**
 * Created by mirco on 13.06.17.
 */
public abstract class AbstractRule implements Rule {

    private boolean active;

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isEditable() {
        return false;
    }
}
