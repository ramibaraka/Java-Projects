package se.sleepdev.ecommerce.model;

public final class User
{
	public static final int EMPTY_USER_ID = -1;
	private final String userName;
	private final String passWord;
	private final int userId;

	public User(String userName, String passWord)
	{
		this(EMPTY_USER_ID, userName, passWord);
	}

	public User(int userId, String userName, String passWord)
	{
		this.userId = userId;
		this.userName = userName;
		this.passWord = passWord;

	}

	public String getUserName()
	{
		return userName;
	}

	public String getPassWord()
	{
		return passWord;
	}

	public int getUserId()
	{
		return userId;
	}

}
