import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.util.Date;
import java.util.regex.*;
import java.text.SimpleDateFormat;

class Jmapi extends MouseAdapter implements ActionListener,TreeSelectionListener
{
	JFrame f;
	State state;
	JSplitPane sp,sp1,sp2;
	JPanel p1,p2,p3,p4,p5,p6,p7;
	JButton compose,receive;
	JTree tree;
	Statement stmt;
	ResultSet rs;
	DefaultTableModel model;
	JTable table;
	JButton reply,forward,delete,deleteall,edit,send,sendall,restore,restoreall;
	String username;
	String password;
	String rows[][];
	JScrollPane pane;
	DefaultMutableTreeNode node;
	String pattern,line;
	Pattern p;
	Matcher m;
	JButton send1,save1,cancel1;
	JTextField t1,t2,t3;
	JLabel l1,l2,l3;
	JTextArea ta;
	JFrame sf;
	JScrollPane pane1;
	Boolean flag,flag1;
	SimpleDateFormat dateFormat;
	Date date;
	String from,to,body,sub;
	int noofclick;
	Boolean flag123,editflag;
	TableColumn column;
	
	public Jmapi()
	{
		flag1=false;
		editflag=false;
		flag123=false;
		username = "jmapidemo@gmail.com";
		password = "javamailapi";
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();
		flag=false;
		f=new JFrame();
		f.setSize(700,700);
		state=new State();
		compose=new JButton("Compose");
		receive=new JButton("Receive All");
		compose.setBounds(20,20,150,50);
		receive.setBounds(20,80,150,50);
	
		compose.addActionListener(this);
		receive.addActionListener(this);
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("");
		DefaultMutableTreeNode inbox = new DefaultMutableTreeNode("Inbox");
		DefaultMutableTreeNode outbox = new DefaultMutableTreeNode("Outbox");
		DefaultMutableTreeNode sentitems = new DefaultMutableTreeNode("Sent Items");
		DefaultMutableTreeNode drafts = new DefaultMutableTreeNode("Drafts");
		DefaultMutableTreeNode deleted = new DefaultMutableTreeNode("Deleted");
		top.add(inbox);
		top.add(outbox);
		top.add(sentitems);
		top.add(drafts);
		top.add(deleted);
		tree=new JTree(top);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    	tree.addTreeSelectionListener(this);
    	
    	String columns[] = {"From", "Subject", "Date"};
    	model = new MyDefaultTableModel(rows, columns);
		table = new JTable(model);
		table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				noofclick=e.getClickCount();
				}
		});
		column = table.getColumn("From");
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
		 public void valueChanged(ListSelectionEvent e) 
		 {
				if(e.getValueIsAdjusting() == false) 
				{
					int row = table.getSelectedRow();
					System.out.println("row="+row);
					row++;
					if (row > 0) 
					{
						if(node.getUserObject()=="Inbox")
						{
							try{
							stmt=state.getStatement();
							rs=stmt.executeQuery("Select * from inbox");
							rs.absolute(row);
							if(noofclick==2)
							{
								getDetailFrame(rs.getString("From"),"",rs.getString("Subject"),rs.getString("Message"));
								table.clearSelection();
								noofclick=0;
							}
							}
							catch(Exception ee)
							{ee.printStackTrace();
							}
							
						}
						else if(node.getUserObject()=="Outbox")
						{
							try{
							stmt=state.getStatement();
							rs=stmt.executeQuery("Select * from outbox");
							rs.absolute(row);
							if(noofclick==2)
							{
								getDetailFrame(rs.getString("From"),rs.getString("To"),rs.getString("Subject"),rs.getString("Message"));	
								table.clearSelection();
								noofclick=0;
							}
							}
							catch(Exception ee)
							{ee.printStackTrace();
							}
						}
						else if(node.getUserObject()=="Sent Items")
						{
							try{
							stmt=state.getStatement();
							rs=stmt.executeQuery("Select * from sent");
							rs.absolute(row);
							if(noofclick==2)
							{
								getDetailFrame(rs.getString("From"),rs.getString("To"),rs.getString("Subject"),rs.getString("Message"));	
								table.clearSelection();
									noofclick=0;
							}
							}
							catch(Exception ee)
							{ee.printStackTrace();}
						}
						else if(node.getUserObject()=="Deleted")
						{
							try{
							stmt=state.getStatement();
							rs=stmt.executeQuery("Select * from deleted");
							rs.absolute(row);
							if(noofclick==2)
							{
								getDetailFrame(rs.getString("From"),rs.getString("To"),rs.getString("Subject"),rs.getString("Message"));	
								table.clearSelection();
									noofclick=0;
							}
							}
							catch(Exception ee)
							{ee.printStackTrace();}
						}
						else if(node.getUserObject()=="Drafts")
						{
							try{
							stmt=state.getStatement();
							rs=stmt.executeQuery("Select * from drafts");
							rs.absolute(row);
							if(noofclick==2)
							{
								getDetailFrame(rs.getString("From"),rs.getString("To"),rs.getString("Subject"),rs.getString("Message"));	
								table.clearSelection();	
								noofclick=0;
							}
							}
							catch(Exception ee)
							{ee.printStackTrace();}
							}
						}
					}
   				}	
		});
		pane = new JScrollPane(table);
		
		p1=new JPanel();
		p1.setLayout(null);
		p1.setPreferredSize(new Dimension(200,160));
		p2=new JPanel();
		p2.setPreferredSize(new Dimension(0,300));
		tree.setPreferredSize(new Dimension(200,0));
		p4=new JPanel();
		p1.add(compose);
		p1.add(receive);
		reply=new JButton("Reply");
		p2.add(reply);
		forward=new JButton("Forward");
		p2.add(forward);
		delete=new JButton("Delete");
		p2.add(delete);
		deleteall=new JButton("Delete All");
		p2.add(deleteall);
		edit=new JButton("Edit");
		p2.add(edit);
		send=new JButton("Send");
		p2.add(send);
		sendall=new JButton("Send All");
		p2.add(sendall);
		restore=new JButton("Restore");
		p2.add(restore);
		restoreall=new JButton("Restore All");
		p2.add(restoreall);
		reply.addActionListener(this);
		forward.addActionListener(this);
		delete.addActionListener(this);
		deleteall.addActionListener(this);
		edit.addActionListener(this);
		send.addActionListener(this);
		sendall.addActionListener(this);
		restore.addActionListener(this);
		restoreall.addActionListener(this);
		
		reply.setEnabled(false);
		forward.setEnabled(false);
		delete.setEnabled(false);
		deleteall.setEnabled(false);
		edit.setEnabled(false);
		send.setEnabled(false);
		sendall.setEnabled(false);
		restore.setEnabled(false);
		restoreall.setEnabled(false);
		
		sp1=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,p1,p2);
		sp2=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,tree,pane);
		sp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,sp1,sp2);
		sp1.setPreferredSize(new Dimension(700,160));
		f.add(sp);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	 public void getFrame(String s)
    {
    	f.setEnabled(false);
    	sf=new JFrame();
    	sf.setSize(420,300);
    	sf.setLayout(null);
    	l1=new JLabel(s);
    	l1.setBounds(20,20,50,30);
    	l2=new JLabel("Subject");
    	l2.setBounds(20,60,50,30);
    	t1=new JTextField();
    	t1.setBounds(80,20,300,30);
    	t2=new JTextField();
    	t2.setBounds(80,60,300,30);
    	ta=new JTextArea();
    	
    	send1=new JButton("send");
    	save1=new JButton("save");
    	cancel1=new JButton("cancel");
    	send1.setBounds(20,210,100,30);
    	save1.setBounds(130,210,100,30);
    	cancel1.setBounds(240,210,100,30);
    	send1.addActionListener(this);
    	save1.addActionListener(this);
    	cancel1.addActionListener(this);	
    	pane1=new JScrollPane(ta);
    	pane1.setBounds(20,100,360,100);
   		sf.add(pane1);
    	sf.add(l1);
    	sf.add(l2);
    	sf.add(t1);
    	sf.add(t2);
    	sf.add(send1);
    	sf.add(save1);
    	sf.add(cancel1);
       	sf.setVisible(true);
       	sf.addWindowListener(new WindowListener()
       	{
       		public void windowClosing(WindowEvent we)
       		{
       		sf.setVisible(false);
    		sf.dispose();
    		f.setEnabled(true);
    		editflag=false;
       		}
       	public void	windowActivated(WindowEvent e) 
       	{}
		public void windowClosed(WindowEvent e) 
		{}
		public void windowDeactivated(WindowEvent e) 
		{}
		public void windowDeiconified(WindowEvent e) 
		{}
		public void windowIconified(WindowEvent e) 
		{}
		public void windowOpened(WindowEvent e) 
		{}

       	});
    }
    public void getDetailFrame(String s1,String s2,String s3,String s4)
    {
    	sf=new JFrame();
    	sf.setSize(420,350);
    	sf.setLayout(null);
    	l1=new JLabel("From");
    	l1.setBounds(20,20,50,30);
    	l2=new JLabel("To");
    	l2.setBounds(20,60,50,30);
    	l3=new JLabel("Subject");
    	l3.setBounds(20,100,50,30);
    	t1=new JTextField();
    	t1.setBounds(80,20,300,30);
    	t1.setText(s1);
    	t2=new JTextField();
    	t2.setBounds(80,60,300,30);
    	t2.setText(s2);
    	t3=new JTextField();
    	t3.setBounds(80,100,300,30);
    	t3.setText(s3);
    	ta=new JTextArea();
    	ta.setText(s4);
    	t1.setEditable(false);
    	t2.setEditable(false);
    	t3.setEditable(false);
    	ta.setEditable(false);
    	
    	cancel1=new JButton("cancel");
    	cancel1.setBounds(20,250,100,30);
    	cancel1.addActionListener(this);	
    	pane1=new JScrollPane(ta);
    	pane1.setBounds(20,140,360,100);
   		sf.add(pane1);
    	sf.add(l1);
    	sf.add(l2);
    	sf.add(l3);
    	sf.add(t1);
    	sf.add(t2);
    	sf.add(t3);
    	sf.add(cancel1);
    	sf.setVisible(true);
    	sf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    }
    public void send()
    {
    	from="jmapidemo@gmail.com";
		String pwd="javamailapi";
		to=t1.getText();
		sub=t2.getText();
		body=ta.getText();

		Message msg;

		Properties props = new Properties();
		props.put("mail.smtp.host","smtp.gmail.com");
		props.put("mail.smtp.port", "465"); 
		props.put("mail.smtp.auth","true");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.debug", "false");
		
		Session session = Session.getDefaultInstance(props,new SimpleMailAuthenticator(from,pwd));
		try
		{
			msg=new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
			msg.setSubject(sub);
			msg.setText(body);
			Transport.send(msg);
			
			try{
			stmt=state.getStatement();
			rs=stmt.executeQuery("Select * from sent");
			rs.moveToInsertRow();
			rs.updateString("From",from);
			rs.updateString("Subject",sub);
			rs.updateString("Date",dateFormat.format(date));
			rs.updateString("Message",body);
			rs.updateString("To",to);
			rs.insertRow();
			}
			catch(SQLException sqle)
			{}

			JOptionPane.showMessageDialog(sf,"Sent successfully");
			sf.setVisible(false);
			sf.dispose();
		}
		catch(MessagingException me)
		{
			try{
			stmt=state.getStatement();
			rs=stmt.executeQuery("Select * from outbox");
			rs.moveToInsertRow();
			rs.updateString("From",from);
			rs.updateString("Subject",sub);
			rs.updateString("Date",dateFormat.format(date));
			rs.updateString("Message",body);
			rs.updateString("To",to);
			rs.insertRow();
		
			
			JOptionPane.showMessageDialog(sf,"Moved to Outbox");
			sf.setVisible(false);
			sf.dispose();
			}
			catch(SQLException sqle)
			{}
			me.printStackTrace();
		}
		if(editflag==true)
		{
					try{
					stmt=state.getStatement();
					rs=stmt.executeQuery("Select * from drafts");
					rs.absolute(table.getSelectedRow()+1);
					rs.deleteRow();
					model.removeRow(table.getSelectedRow());}
					catch(SQLException ee)
					{}
		}
			editflag=false;
		f.setEnabled(true);
    }
    public void sendoutbox()
    {
    	from="jmapidemo@gmail.com";
			String pwd="javamailapi";
		try{	to=rs.getString("To");
			sub=rs.getString("Subject");
			body=rs.getString("Message");}
			catch(SQLException ee)
			{}
	
			Message msg;
	
			Properties props = new Properties();
			props.put("mail.smtp.host","smtp.gmail.com");
			props.put("mail.smtp.port", "465"); 
			props.put("mail.smtp.auth","true");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.debug", "false");
	
			Session session = Session.getDefaultInstance(props,new SimpleMailAuthenticator(from,pwd));
			try
			{
				msg=new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));
				msg.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
				msg.setSubject(sub);
				msg.setText(body);
				Transport.send(msg);
				
				try{
				stmt=state.getStatement();
				rs=stmt.executeQuery("Select * from sent");
				rs.moveToInsertRow();
				rs.updateString("From",from);
				rs.updateString("Subject",sub);
				rs.updateString("Date",dateFormat.format(date));
				rs.updateString("Message",body);
				rs.updateString("To",to);
				rs.insertRow();
				stmt=state.getStatement();
				rs=stmt.executeQuery("Select * from outbox");
				rs.absolute(table.getSelectedRow()+1);
				rs.deleteRow();
				model.removeRow(table.getSelectedRow());
				}
				catch(SQLException sqle)
				{}
	
				JOptionPane.showMessageDialog(f,"Sent successfully");
			}
			catch(MessagingException me)
			{
				JOptionPane.showMessageDialog(f,"Connect to the internet");
				flag123=true;
			}
    }
    public void save()
    {
    	if(editflag==true)
    	{
    		try{
    			int x=table.getSelectedRow();
				stmt=state.getStatement();
				rs=stmt.executeQuery("Select * from drafts");
				rs.absolute(table.getSelectedRow()+1);
				rs.updateString("From","jmapidemo@gmail.com");
				rs.updateString("Subject",t2.getText());
				rs.updateString("Date",dateFormat.format(date));
				rs.updateString("Message",ta.getText());
				rs.updateString("To",t1.getText());
				rs.updateRow();
				model.removeRow(x);
				model.insertRow(x, new String[]{rs.getString("From"), rs.getString("Subject"), rs.getString("Date")});
				}
				catch(SQLException sqle)
				{}
    	}
    	else
    	{
	    	try{
				stmt=state.getStatement();
				rs=stmt.executeQuery("Select * from drafts");
				rs.moveToInsertRow();
				rs.updateString("From","jmapidemo@gmail.com");
				rs.updateString("Subject",t2.getText());
				rs.updateString("Date",dateFormat.format(date));
				rs.updateString("Message",ta.getText());
				rs.updateString("To",t1.getText());
				rs.insertRow();
				}
				catch(SQLException sqle)
				{}
    	}
    		editflag=false;
	    	JOptionPane.showMessageDialog(sf,"Saved to Drafts");
			sf.setVisible(false);
			sf.dispose();
			f.setEnabled(true);
    }
	public void check()
	{
		flag=false;
		int abc=0;
		line=t1.getText();
		pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
		p=Pattern.compile(pattern);
		m=p.matcher(line);
		if(m.find())
		{
			abc=1;
			flag=true;
		}
		if(t2.getText().equals(""))
		{
			flag=false;
		}
		if(ta.getText().equals(""))
		{
			flag=false;
		}
	
		if(abc==0)
    		{
    			JOptionPane.showMessageDialog(sf,"Please enter valid email address");
    		}
    		else if(t2.getText().equals(""))
    		{
    			JOptionPane.showMessageDialog(sf,"Please enter Subject");
    		}
    		else if(ta.getText().equals(""))
    		{
    				JOptionPane.showMessageDialog(sf,"Please enter Message body");
    		}
	}
	public void check1()
	{
		flag1=false;
		if(t1.getText().equals("")&&t2.getText().equals("")&&ta.getText().equals(""))
		{	
			flag1=false;
			JOptionPane.showMessageDialog(sf,"Cannot leave all the fields blank");
		}
		else
		{flag1=true;}
	}
	public void removerows()
	{
		int rowNum=table.getRowCount();
					for(int i=rowNum;i>0;i--)
					{
						rowNum=table.getRowCount();
						model.removeRow(rowNum-1);
					}
	}
	public void delete()
	{
		if(node.getUserObject()=="Inbox")
    			{
    				try{
    					String to1,from1,sub1,body1,date1;
    				stmt=state.getStatement();
    				rs=stmt.executeQuery("Select * from inbox");
    				rs.absolute(table.getSelectedRow()+1);
    				to1=rs.getString("To");
    				from1=rs.getString("From");
    				sub1=rs.getString("Subject");
    				body1=rs.getString("Message");
    				date1=rs.getString("Date");
    				rs.deleteRow();
    				model.removeRow(table.getSelectedRow());
    				
    				stmt=state.getStatement();
    				rs=stmt.executeQuery("Select * from deleted");
    				rs.moveToInsertRow();
					rs.updateString("From",from1);
					rs.updateString("Subject",sub1);
					rs.updateString("Date",date1);
					rs.updateString("Message",body1);
					rs.updateString("To",to1);
					rs.updateString("Delfrom","i");
					rs.insertRow();
    				}
    				catch(SQLException ee)
    				{}
    			}
    			else if(node.getUserObject()=="Sent Items")
    			{
    				try{
    					String to1,from1,sub1,body1,date1;
    				stmt=state.getStatement();
    				rs=stmt.executeQuery("Select * from sent");
    				rs.absolute(table.getSelectedRow()+1);
    				to1=rs.getString("To");
    				from1=rs.getString("From");
    				sub1=rs.getString("Subject");
    				body1=rs.getString("Message");
    				date1=rs.getString("Date");
    				rs.deleteRow();
    				model.removeRow(table.getSelectedRow());
    				
    				stmt=state.getStatement();
    				rs=stmt.executeQuery("Select * from deleted");
    				rs.moveToInsertRow();
					rs.updateString("From",from1);
					rs.updateString("Subject",sub1);
					rs.updateString("Date",date1);
					rs.updateString("Message",body1);
					rs.updateString("To",to1);
					rs.updateString("Delfrom","s");
					rs.insertRow();
    				}
    				catch(SQLException ee)
    				{}
    			}
    			else if(node.getUserObject()=="Drafts")
    			{
    				try{
    					String to1,from1,sub1,body1,date1;
    				stmt=state.getStatement();
    				rs=stmt.executeQuery("Select * from drafts");
    				rs.absolute(table.getSelectedRow()+1);
    				to1=rs.getString("To");
    				from1=rs.getString("From");
    				sub1=rs.getString("Subject");
    				body1=rs.getString("Message");
    				date1=rs.getString("Date");
    				rs.deleteRow();
    				model.removeRow(table.getSelectedRow());
    				
    				stmt=state.getStatement();
    				rs=stmt.executeQuery("Select * from deleted");
    				rs.moveToInsertRow();
					rs.updateString("From",from1);
					rs.updateString("Subject",sub1);
					rs.updateString("Date",date1);
					rs.updateString("Message",body1);
					rs.updateString("To",to1);
					rs.updateString("Delfrom","d");
					rs.insertRow();
    				}
    				catch(SQLException ee)
    				{}
    			}
    			else if(node.getUserObject()=="Outbox")
    			{
    				try{
    					String to1,from1,sub1,body1,date1;
    				stmt=state.getStatement();
    				rs=stmt.executeQuery("Select * from outbox");
    				rs.absolute(table.getSelectedRow()+1);
    				to1=rs.getString("To");
    				from1=rs.getString("From");
    				sub1=rs.getString("Subject");
    				body1=rs.getString("Message");
    				date1=rs.getString("Date");
    				rs.deleteRow();
    				model.removeRow(table.getSelectedRow());
    				
    				stmt=state.getStatement();
    				rs=stmt.executeQuery("Select * from deleted");
    				rs.moveToInsertRow();
					rs.updateString("From",from1);
					rs.updateString("Subject",sub1);
					rs.updateString("Date",date1);
					rs.updateString("Message",body1);
					rs.updateString("To",to1);
					rs.updateString("Delfrom","o");
					rs.insertRow();
    				}
    				catch(SQLException ee)
    				{}
    			}
    			else if(node.getUserObject()=="Deleted")
    			{
    				try{
    				stmt=state.getStatement();
    				rs=stmt.executeQuery("Select * from deleted");
    				rs.absolute(table.getSelectedRow()+1);
    				rs.deleteRow();
    				model.removeRow(table.getSelectedRow());
    				}
    				catch(SQLException ee)
    				{}
    			}
	}
	public void restore()
	{
    			try{
    				String to1,from1,sub1,body1,date1,delfrom;
    			stmt=state.getStatement();
    			rs=stmt.executeQuery("Select * from deleted");
    			rs.absolute(table.getSelectedRow()+1);
    			to1=rs.getString("To");
    			from1=rs.getString("From");
    			sub1=rs.getString("Subject");
    			body1=rs.getString("Message");
    			date1=rs.getString("Date");
    			delfrom=rs.getString("Delfrom");
    			rs.deleteRow();
    			model.removeRow(table.getSelectedRow());
    			if(delfrom.equals("i"))
    			{
    			stmt=state.getStatement();
    			rs=stmt.executeQuery("Select * from inbox");
    			rs.moveToInsertRow();
				rs.updateString("From",from1);
				rs.updateString("Subject",sub1);
				rs.updateString("Date",date1);
				rs.updateString("Message",body1);
				rs.updateString("To",to1);
				rs.insertRow();
    			}
    			else if(delfrom.equals("s"))
    			{
    				stmt=state.getStatement();
    			rs=stmt.executeQuery("Select * from sent");
    			rs.moveToInsertRow();
				rs.updateString("From",from1);
				rs.updateString("Subject",sub1);
				rs.updateString("Date",date1);
				rs.updateString("Message",body1);
				rs.updateString("To",to1);
				rs.insertRow();
    			}
    			else if(delfrom.equals("d"))
    			{
    				stmt=state.getStatement();
    			rs=stmt.executeQuery("Select * from drafts");
    			rs.moveToInsertRow();
				rs.updateString("From",from1);
				rs.updateString("Subject",sub1);
				rs.updateString("Date",date1);
				rs.updateString("Message",body1);
				rs.updateString("To",to1);
				rs.insertRow();
    			}
    			else if(delfrom.equals("o"))
    			{
    				stmt=state.getStatement();
    			rs=stmt.executeQuery("Select * from outbox");
    			rs.moveToInsertRow();
				rs.updateString("From",from1);
				rs.updateString("Subject",sub1);
				rs.updateString("Date",date1);
				rs.updateString("Message",body1);
				rs.updateString("To",to1);
				rs.insertRow();
    			}
    			
    			}
    			catch(SQLException ee)
    			{}	
	}
	 public void valueChanged(TreeSelectionEvent e) {
    	node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
    	System.out.println(node.getUserObject());
    	if(node.getUserObject()=="Inbox")
    	{
    	reply.setEnabled(true);
		forward.setEnabled(true);
		delete.setEnabled(true);
		deleteall.setEnabled(true);
		edit.setEnabled(false);
		send.setEnabled(false);
		sendall.setEnabled(false);
		restore.setEnabled(false);
		restoreall.setEnabled(false);
    		removerows();
    		column.setHeaderValue("From");
				table.getTableHeader().repaint();
    		try{
    		stmt=state.getStatement();
    		rs=stmt.executeQuery("Select * from inbox");
    		while(rs.next())
    		{
    			model.addRow(new String[]{rs.getString("From"), rs.getString("Subject"), rs.getString("Date")});
    		}
    		}
    		catch(SQLException ee)
    		{
    			ee.printStackTrace();
    		}
    	}
    	else if(node.getUserObject()=="Outbox")
    	{
    		reply.setEnabled(false);
		forward.setEnabled(false);
		delete.setEnabled(true);
		deleteall.setEnabled(true);
		edit.setEnabled(false);
		send.setEnabled(true);
		sendall.setEnabled(true);
		restore.setEnabled(false);
		restoreall.setEnabled(false);
    		removerows();
				column.setHeaderValue("To");
				table.getTableHeader().repaint();
    		try{
    		stmt=state.getStatement();
    		rs=stmt.executeQuery("Select * from outbox");
    		while(rs.next())
    		{
    			model.addRow(new String[]{rs.getString("To"), rs.getString("Subject"), rs.getString("Date")});
    		}
    		}
    		catch(SQLException ee)
    		{ee.printStackTrace();}
    	}
    	else if(node.getUserObject()=="Drafts")
    	{
    		reply.setEnabled(false);
		forward.setEnabled(true);
		delete.setEnabled(true);
		deleteall.setEnabled(true);
		edit.setEnabled(true);
		send.setEnabled(false);
		sendall.setEnabled(false);
		restore.setEnabled(false);
		restoreall.setEnabled(false);
		column.setHeaderValue("From");
				table.getTableHeader().repaint();
    		removerows();
    		try{
    		stmt=state.getStatement();
    		rs=stmt.executeQuery("Select * from drafts");
    		while(rs.next())
    		{
    			model.addRow(new String[]{rs.getString("From"), rs.getString("Subject"), rs.getString("Date")});
    		}
    		}
    		catch(SQLException ee)
    		{ee.printStackTrace();}
    	}
    	else if(node.getUserObject()=="Sent Items")
    	{
    		reply.setEnabled(false);
		forward.setEnabled(true);
		delete.setEnabled(true);
		deleteall.setEnabled(true);
		edit.setEnabled(false);
		send.setEnabled(false);
		sendall.setEnabled(false);
		restore.setEnabled(false);
		restoreall.setEnabled(false);
    		removerows();
    		column.setHeaderValue("To");
				table.getTableHeader().repaint();
    		try{
    		stmt=state.getStatement();
    		rs=stmt.executeQuery("Select * from sent");
    		while(rs.next())
    		{
    			model.addRow(new String[]{rs.getString("To"), rs.getString("Subject"), rs.getString("Date")});
    		}
    		}
    		catch(SQLException ee)
    		{ee.printStackTrace();}
    	}
    	else if(node.getUserObject()=="Deleted")
    	{
    		reply.setEnabled(false);
		forward.setEnabled(false);
		delete.setEnabled(true);
		deleteall.setEnabled(true);
		edit.setEnabled(false);
		send.setEnabled(false);
		sendall.setEnabled(false);
		restore.setEnabled(true);
		restoreall.setEnabled(true);
    		removerows();
    		column.setHeaderValue("From");
				table.getTableHeader().repaint();
    		try{
    		stmt=state.getStatement();
    		rs=stmt.executeQuery("Select * from deleted");
    		while(rs.next())
    		{
    			model.addRow(new String[]{rs.getString("From"), rs.getString("Subject"), rs.getString("Date")});
    		}
    		}
    		catch(SQLException ee)
    		{ee.printStackTrace();}
    	}
    	
    }
   
    public void actionPerformed(ActionEvent e)
	{
		String str=e.getActionCommand();
		if(str=="Compose")
		{
			getFrame("To");
		}
		else if(str=="Receive All")
		{
			try
			{
			stmt=state.getStatement();
			rs=stmt.executeQuery("Select * from inbox");
			Properties props = new Properties();
			props.put("mail.imap.host", "imap.gmail.com");
			props.put("mail.imap.port", "993");
			props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
			Session session = Session.getInstance(props,null);
			Store store = session.getStore("imap");
			store.connect(username,password);
			rs.last();
			for(int i=rs.getRow();i>0;i--)
			{
				rs.first();
				rs.deleteRow();
			}
			Folder inboxFolder = store.getFolder("inbox");
			inboxFolder.open(Folder.READ_ONLY);

			Message[] arr = inboxFolder.getMessages();

			System.out.println("No of Message : "+arr.length);
			for(int i=0; i<arr.length;i++)
			{
				rs.moveToInsertRow();
				System.out.println("\n--------------------------Message"+(i+1)+"--------------------------");
				Address[] from = arr[i].getFrom();
				System.out.println("From : " + from[0]);
				System.out.println("Subject : " + arr[i].getSubject());
				System.out.println("Date : " + arr[i].getSentDate());
				System.out.println("Message : " + arr[i].getContent());
				rs.updateString("From",from[0]+"");
				rs.updateString("Subject",arr[i].getSubject()+"");
				rs.updateString("Date",arr[i].getSentDate()+"");
				rs.updateString("Message",arr[i].getContent()+"");
				rs.updateString("To","");
				rs.insertRow();
			}
			inboxFolder.close(true);
			store.close();
			}
			catch(Exception ee)
			{
				JOptionPane.showMessageDialog(f,"Please connect to the internet");
				ee.printStackTrace();
			}
		}
		else if(str=="save")
    	{
    		check1();
    		if(flag1==true)
    		{
    			save();
    		}
    		
    	}
    	else if(str=="send")
    	{
    		check();
    		if(flag==true)
    		{
    			send();
    		}
    	}
    	else if(str=="cancel")
    	{
    		sf.setVisible(false);
    		sf.dispose();
    		f.setEnabled(true);
    		editflag=false;
    	}
    	else if(str=="Reply")
    	{
    		int sel=table.getSelectedRow();
    		if(sel!=-1)
    		{
 			getFrame("To");
    		try{
    		t1.setText(rs.getString("From"));
    		}
    		catch(SQLException ee)	{}
    		}
    
    	}
    	else if(str=="Forward")
    	{
	    		int sel=table.getSelectedRow();
	    		if(sel!=-1)
	    		{
	 			getFrame("To");
	    		try{
	    		t2.setText(rs.getString("Subject"));
	    		ta.setText(rs.getString("Message"));
	    		}
	    		catch(SQLException ee)	{}
	    		}
    	}
    	else if(str=="Send")
    	{
    		int sel=table.getSelectedRow();
    		if(sel!=-1)
    		{
    			sendoutbox();
    		}
    	}
    	else if(str=="Send All")
    	{
    		try{
    		stmt=state.getStatement();
    		rs=stmt.executeQuery("Select * from outbox");
    		rs.last();
    		for(int i=rs.getRow();i>0;i--)
    		{
    			table.setRowSelectionInterval(i-1, i-1);
    			rs.absolute(i);
    			sendoutbox();
    			if(flag123==true)
    				break;
    		}
    		
    		}
    		catch(SQLException ee)
    		{}
    	}
    	else if(str=="Edit")
    	{
    		int sel=table.getSelectedRow();
    		if(sel!=-1)
    		{
    			try{
    			stmt=state.getStatement();
    			rs=stmt.executeQuery("Select * from drafts");
    			rs.absolute(table.getSelectedRow()+1);
    			getFrame("To");
    			t1.setText(rs.getString("To"));
    			t2.setText(rs.getString("Subject"));
    			ta.setText(rs.getString("Message"));
    			editflag=true;
    			}
    			catch(SQLException ee)
    			{}
    		}
    	}
    	else if(str=="Delete")
    	{
    		int sel=table.getSelectedRow();
    		if(sel!=-1)
    		{
    			delete();
    		}
    	}
    	else if(str=="Delete All")
    	{
    		table.clearSelection();
    		if(node.getUserObject()=="Inbox")
    		{
    			try{
	    		stmt=state.getStatement();
	    		rs=stmt.executeQuery("Select * from inbox");
	    		rs.last();
	    		for(int i=rs.getRow();i>0;i--)
	    		{
	    			table.setRowSelectionInterval(i-1, i-1);
	    			rs.absolute(i);
	    			delete();
	    			System.out.print(i);
	    		}
	    		}
	    		catch(SQLException ee)
	    		{}
    		}
    		else if(node.getUserObject()=="Sent Items")
    		{
    			try{
	    		stmt=state.getStatement();
	    		rs=stmt.executeQuery("Select * from sent");
	    		rs.last();
	    		for(int i=rs.getRow();i>0;i--)
	    		{
	    			table.setRowSelectionInterval(i-1, i-1);
	    			rs.absolute(i);
	    			delete();
	    		}
	    		}
	    		catch(SQLException ee)
	    		{}
    		}
    		else if(node.getUserObject()=="Drafts")
    		{
    			try{
	    		stmt=state.getStatement();
	    		rs=stmt.executeQuery("Select * from drafts");
	    		rs.last();
	    		for(int i=rs.getRow();i>0;i--)
	    		{
	    			table.setRowSelectionInterval(i-1, i-1);
	    			rs.absolute(i);
	    			delete();
	    		}
	    		}
	    		catch(SQLException ee)
	    		{}
    		}
    		else if(node.getUserObject()=="Outbox")
    		{
    			try{
	    		stmt=state.getStatement();
	    		rs=stmt.executeQuery("Select * from outbox");
	    		rs.last();
	    		for(int i=rs.getRow();i>0;i--)
	    		{
	    			table.setRowSelectionInterval(i-1, i-1);
	    			rs.absolute(i);
	    			delete();
	    		}
	    		}
	    		catch(SQLException ee)
	    		{}
    		}
    		else if(node.getUserObject()=="Deleted")
    		{
    			try{
	    		stmt=state.getStatement();
	    		rs=stmt.executeQuery("Select * from deleted");
	    		rs.last();
	    		for(int i=rs.getRow();i>0;i--)
	    		{
	    			table.setRowSelectionInterval(i-1, i-1);
	    			rs.absolute(i);
	    			rs.deleteRow();
	    			model.removeRow(table.getSelectedRow());
	    		}
	    		}
	    		catch(SQLException ee)
	    		{}
    		}
    	}
    	else if(str=="Restore")
    	{
    		int sel=table.getSelectedRow();
    		if(sel!=-1)
    		{
    			restore();
    		}
    	}
    	else if(str=="Restore All")
    	{
    		try{
	    		stmt=state.getStatement();
	    		rs=stmt.executeQuery("Select * from deleted");
	    		rs.last();
	    		for(int i=rs.getRow();i>0;i--)
	    		{
	    			table.setRowSelectionInterval(i-1, i-1);
	    			rs.absolute(i);
	    			restore();
	    		}
	    		}
	    		catch(SQLException ee)
	    		{}
    	}
	}
	
	public static void main(String args[])
	{
		Jmapi jm=new Jmapi();
	}
	
}