package test;

/** Indicates that a test has failed. */
public class TestFailed extends Exception
{
    /** Constructs a <code>TestFailed</code> object. */
    protected TestFailed()
    {
    }

    /** Constructs a <code>TestFailed</code> object from a message string. */
    public TestFailed(String message)
    {

        super(message);
        System.out.println(message);
    }

    /** Constructs a <code>TestFailed</code> object from a message string and an
        underlying cause. */
    public TestFailed(String message, Throwable cause)
    {
        super(message, cause);
        System.out.println(message);
    }
}
