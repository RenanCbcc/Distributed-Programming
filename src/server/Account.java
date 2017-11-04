package server;

import javax.swing.JOptionPane;

public class Account {

	private String name;
	private int id;

	public Account(String name, int id) {
		super();
		setId(id);
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (!(name.length() > 100) && !(name.length() <= 0)) {
			this.name = name;
		}

		IllegalArgumentException ex = new IllegalArgumentException();
		String str = "An exception has occurred!" + "\n" + ex.getCause();
		JOptionPane.showMessageDialog(null, str, "Error", 0);

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		try {
			this.id = id;
		} catch (IllegalArgumentException ex) {
			String str = "An exception has occurred!" + "\n" + ex.getCause();
			JOptionPane.showMessageDialog(null, str, "Error", 0);
		}

	}

	@Override
	public String toString() {
		return "Account [name=" + name + ", id=" + id + "]";
	}

}
