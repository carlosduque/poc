package o.db.model;

import java.io.*;
import java.util.*;
import org.hibernate.*;
import org.hibernate.cfg.*;

public class NewHiberConsole {

	public static void main(String args[]) {
		SessionFactory factory = null;
		System.out.println("Creating factory");

		try {
			Configuration cfg = new Configuration();
			cfg.addClass(Player.class);

			factory = cfg.buildSessionFactory();
			System.out.println("factory ready");

		} catch (Exception e1) {
			System.out.println("" + e1);
		}

		// -----------------------------------------

		try {
			String s;
			BufferedReader dis = new BufferedReader(new InputStreamReader(System.in));
			Transaction tx = null;
			do {
				System.out.println("add / showall/find / remove/update");
				s = dis.readLine();
				// ---------------
				if (s.equals("add")) {
					System.out.println("What name? ");
					String a = dis.readLine();
					System.out.println("What Place? ");
					String b = dis.readLine();
					try {
						Session session = factory.openSession();
						tx = session.beginTransaction();
						Player Player1 = new Player(a, b);
						session.save(Player1);
						tx.commit();
						session.flush();
						session.close();
						System.out.println("added");
					} catch (Exception e1) {
						System.out.println("" + e1);
					}
				}
				// -------------------------------------
				if (s.equals("remove")) {
					try {
						System.out.println("What key? ");
						String a = dis.readLine();
						Session session = factory.openSession();
						tx = session.beginTransaction();
						int i = Integer.parseInt(a);
						Player Player1 = (Player) session.get(Player.class,
								new Integer(i));
						session.delete(Player1);
						tx.commit();
						session.flush();
						session.close();
						System.out.println("removed");
					} catch (Exception e1) {
						System.out.println("" + e1);
					}
				}
				// --------------------------
				if (s.equals("showall")) {
					try {
						Session session = factory.openSession();
						tx = session.beginTransaction();
						java.util.List list1 = ((org.hibernate.classic.Session) session).find("from Player");
						Iterator i = list1.iterator();
						while (i.hasNext()) {
							Player Player1 = (Player) i.next();
							System.out.println(Player1.getId());
							System.out.println(Player1.getName());
							System.out.println(Player1.getPlace());
							System.out.println("---------------");
						}
						tx.commit();
						session.flush();
						session.close();
					} catch (Exception e1) {
						System.out.println("" + e1);
					}
				}
				// -----------------------------------------
				if (s.equals("find")) {
					try {
						System.out.println("What key ");
						String a = dis.readLine();
						Session session = factory.openSession();
						tx = session.beginTransaction();
						int i = Integer.parseInt(a);
						Player Player1 = (Player) session.get(Player.class,
								new Integer(i));
						String n = Player1.getName();
						String m = Player1.getPlace();
						System.out.println(n + "\t" + m);
						tx.commit();
						session.flush();
						session.close();
					} catch (Exception e1) {
						System.out.println("" + e1);
					}
				}
				// ------------------------------------------
				if (s.equals("update")) {
					try {
						System.out.println("What key ");
						String a = dis.readLine();
						Session session = factory.openSession();
						tx = session.beginTransaction();
						int i = Integer.parseInt(a);
						Player Player1 = (Player) session.get(Player.class,
								new Integer(i));
						System.out.println("what name?");
						String s1 = dis.readLine();

						System.out.println("what place?");
						String s2 = dis.readLine();
						Player1.setName(s1);
						Player1.setPlace(s2);
						System.out.println("updated");
						tx.commit();
						session.flush();
						session.close();
					} catch (Exception e1) {
						System.out.println("" + e1);
					}
				}
				// -----------------------------------
			} while (!s.equals("over"));
		} catch (Exception e1) {
			System.out.println("" + e1);
		}
	}
}
// ============================================
