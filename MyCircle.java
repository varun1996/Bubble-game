import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
class Var
{
	public static  Thread t=null;
}
class MyCircle extends Frame implements Runnable,ActionListener,MouseListener,ItemListener
{
	int index=0; int l=10; int x,y; boolean flag=false;
	Random robj= new Random();
	Color car[]={Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW};
	int score=0;
	//Thread t;
	Button btn_red,btn_green,btn_blue,btn_yellow,btn_reset,btn_start,btn_stop;
	TextField tf,tf2,tf3;
	Label lb,lb_level;
	Graphics mine=null;
	Color ck=Color.RED;
	Font my_font= new Font("courier",Font.BOLD,30);
	Checkbox cb_easy,cb_medium,cb_hard;
	CheckboxGroup gp;
	MyCircle()
	{
		setBackground(new Color(100,100,100));
		setTitle("Clicking Game");
		setLayout(new FlowLayout(FlowLayout.CENTER,15,15));
		gp= new CheckboxGroup();
		add(btn_red=   new Button("  RED "));   btn_red.addActionListener(this);
		add(btn_green= new Button(" GREEN"));   btn_green.addActionListener(this);
		add(btn_blue=  new Button(" BLUE "));   btn_blue.addActionListener(this);
		add(btn_yellow=new Button("YELLOW"));   btn_yellow.addActionListener(this);
		add(btn_reset=new Button("Reset Game"));   btn_reset.addActionListener(this);
		add(btn_start=new Button("START"));         btn_start.addActionListener(this);
		add(btn_stop= new Button("STOP"));		btn_stop.addActionListener(this);
		add(lb= new Label("SCORE:")); lb.setFont(my_font); lb.setForeground(Color.RED);
		add(tf2= new TextField(5));   tf2.setEnabled(false);
		add(tf= new TextField(10)); tf.setEnabled(false);
		//add(new Label("                   "));
		add(tf3=new TextField(20));		tf3.setText("RED"); tf3.setEnabled(false);
		add(lb_level=new Label("Level:")); lb_level.setFont(my_font); lb_level.setForeground(Color.RED);
		add(cb_easy=new Checkbox("EASY",gp,true));		cb_easy.addItemListener(this); //cb_easy.setState(true);
		add(cb_medium=new Checkbox("MEDIUM",gp,false));		cb_medium.addItemListener(this); //cb_medium.setEnabled(false);
	   	add(cb_hard=new Checkbox("HARD",gp,false));		cb_hard.addItemListener(this);			//cb_hard.setEnabled(false);
		//System.out.println(gp.getSelectedCheckbox());
		this.addMouseListener(this);
		this.addWindowListener(new MyWindow());	
		Var.t=new Thread(this,"mine");
		setSize(600,600);  setVisible(true);
	    //try{t.sleep(1000);} catch(InterruptedException eee) {}
		//t.start();
		
	}

	public void paint(Graphics g)
	{
		
		x=robj.nextInt(350)+34;
		y=robj.nextInt(200)+160;
		g.setColor(car[robj.nextInt(4)]);
		g.drawRect(30,158,520,400);
		g.fillOval(x,y,50,50);
		g.fillOval(x+40,y+40,50,50);
		mine=g;
	}
	
	public static void main(String[] args) 
	{
		new MyCircle();
	}
	
	public void run()
	{
		while(true)
		{
			
			try{Thread.sleep(100*l);} catch(InterruptedException e) {}
			repaint();
             
		}
	}

	public void actionPerformed(ActionEvent ee)
	{ 
		Button temp= (Button) ee.getSource();
		
		if(temp==btn_red)
		{
		ck=Color.RED;
		tf3.setText("RED");
		}
		
		else if(temp==btn_green)
		{
		ck=Color.GREEN;
		tf3.setText("GREEN");
		}
			
		else if(temp==btn_blue)
		{
		ck=Color.BLUE;
		tf3.setText("BLUE");
		}
			
	    else if(temp==btn_yellow)
		{
		ck=Color.YELLOW;
		tf3.setText("YELLOW");
		}

			
		else if(temp==btn_reset)
		{
		tf2.setText("");
		tf.setText("New Game");
		score=0;
		ck=Color.RED;
		tf3.setText("RED");
		}

		else if(temp==btn_start)
		{
			if(flag)
			{
				Var.t= new Thread(this);
				ck=Color.RED;
				tf3.setText("RED");
				Var.t.start();

			}
			else
			{
				Var.t.start();
				
			}
			tf.setText("Game Begins");
			tf2.setText("");
			score=0;
			btn_start.setEnabled(false);
		}

		else if(temp==btn_stop)
		{

			Var.t.stop();
			flag=true;
			tf.setText("Game Stopped");
			btn_start.setEnabled(true);
		}
		}

    public void	mouseClicked(MouseEvent e)
    {
			Color cobj= mine.getColor();
			if(score>-5)
			{
					if(cobj==ck)
					{	tf.setText("you win");  score++; 
							tf2.setText(String.valueOf(score));
					}
					 else
						{  tf.setText("you lost");  score--;
								tf2.setText(String.valueOf(score));
						}

			}		

			else
			{
				
				Var.t.stop();
				btn_stop.setEnabled(false);
				btn_start.setEnabled(true);
				flag=true;
				JOptionPane.showMessageDialog(this,"Game Over","End Game",JOptionPane.ERROR_MESSAGE);
			
			}


		}

	public	void	mouseEntered(MouseEvent e)
		{}
	public	void	mouseExited(MouseEvent e)
		{}
	public	void	mousePressed(MouseEvent e)
		{}
	public	void	mouseReleased(MouseEvent e)
		{}
	public void itemStateChanged(ItemEvent e)
		{
			Checkbox temp;
			temp=gp.getSelectedCheckbox();

			if(temp==cb_easy)
				l=10;
                

			else if(temp==cb_medium)
				l=5;
				
			else if(temp==cb_hard)
					l=1;
			
			}
}

class MyWindow extends WindowAdapter
{
	public void windowClosing(WindowEvent e)
	{
		Frame f=(Frame) e.getSource();
		//Thread ts=Thread.currentThread();
		Var.t.stop();
		f.dispose();
	}
}