package se.sleepdev.ecommerce.service;

public final class RepositryException extends Exception
{
	private static final long serialVersionUID = 1914695986368216227L;

	public RepositryException(String message)
	{
		super(message);
	}

	public RepositryException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
}
