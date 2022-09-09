package RestorableQueue;

public interface Restorable
{
	public void saveState();
	public boolean revertState();
}
