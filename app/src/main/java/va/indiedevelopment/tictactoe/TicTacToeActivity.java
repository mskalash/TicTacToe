package va.indiedevelopment.tictactoe;
 
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
 
 
public class TicTacToeActivity extends Activity {
	private TicTacToeGame mGame;
    private ImageButton newgame;
    private ImageView finish;
	private ImageButton mBoardButtons[];
	private TextView mInfoTextView;
	private TextView mHumanCount;
	private TextView mTieCount;
	private TextView mAndroidCount;
	private int mHumanCounter = 0;
	private int mTieCounter = 0;
	private int mAndroidCounter = 0;
	private boolean mHumanFirst = true;
	private boolean mGameOver = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         finish= (ImageView) findViewById(R.id.finish);
        newgame = (ImageButton) findViewById(R.id.new1);
        mBoardButtons = new ImageButton[mGame.getBOARD_SIZE()];
        mBoardButtons[0] = (ImageButton) findViewById(R.id.one);
        mBoardButtons[1] = (ImageButton) findViewById(R.id.two);
        mBoardButtons[2] = (ImageButton) findViewById(R.id.three);
        mBoardButtons[3] = (ImageButton) findViewById(R.id.four);
        mBoardButtons[4] = (ImageButton) findViewById(R.id.five);
        mBoardButtons[5] = (ImageButton) findViewById(R.id.six);
        mBoardButtons[6] = (ImageButton) findViewById(R.id.seven);
        mBoardButtons[7] = (ImageButton) findViewById(R.id.eight);
        mBoardButtons[8] = (ImageButton) findViewById(R.id.nine);
 
        mInfoTextView = (TextView) findViewById(R.id.information);
        mHumanCount = (TextView) findViewById(R.id.humanCount);
        mTieCount = (TextView) findViewById(R.id.tiesCount);
        mAndroidCount = (TextView) findViewById(R.id.androidCount);
 
        mHumanCount.setText(Integer.toString(mHumanCounter));
        mTieCount.setText(Integer.toString(mTieCounter));
        mAndroidCount.setText(Integer.toString(mAndroidCounter));
 
        mGame = new TicTacToeGame();

        startNewGame();

		View.OnClickListener oclBtnOk = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startNewGame();
			}
		};

		// присвоим обработчик кнопке OK (btnOk)
		newgame.setOnClickListener(oclBtnOk);
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.game_menu, menu);
 
    	return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    	case R.id.newGame:
    		startNewGame();
    		break;
    	case R.id.exitGame:
    		TicTacToeActivity.this.finish();
    		break;
    	}
 
    	return true;
    }
 
    private void startNewGame()
    {
    	mGame.clearBoard();

    	for (int i = 0; i < mBoardButtons.length; i++)
    	{
    		mBoardButtons[i].setImageResource(R.drawable.bombsite_a);
    		mBoardButtons[i].setEnabled(true);
    		mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
    	}
 
    	if (mHumanFirst)
    	{
    		mInfoTextView.setText(R.string.first_human);
    		mHumanFirst = false;
    	}
    	else
    	{

    		int move = mGame.getComputerMove();
    		setMove(mGame.ANDROID_PLAYER, move);
    		mHumanFirst = true;
    	}
 
    	mGameOver = false;
    }
 
    private class ButtonClickListener implements View.OnClickListener
    {
    	int location;

    	public ButtonClickListener(int location)
    	{
    		this.location = location;
    	}
 
    	public void onClick(View view)
    	{

    		if (!mGameOver)
    		{
    			if (mBoardButtons[location].isEnabled())
    			{
    				setMove(mGame.HUMAN_PLAYER, location);
 
    				int winner = mGame.checkForWinner();
 
    				if (winner == 0)
    				{

    					int move = mGame.getComputerMove();
    					setMove(mGame.ANDROID_PLAYER, move);
    					winner = mGame.checkForWinner();    					
    				}
 
    				if (winner == 0)
    					mInfoTextView.setText(R.string.first_human);
    				else if (winner == 1)
    				{
    					mInfoTextView.setText(R.string.result_tie);
    					mTieCounter++;
    					mTieCount.setText(Integer.toString(mTieCounter));
    					mGameOver = true;
    				}
    				else if (winner == 2)
    				{
    					mInfoTextView.setText(R.string.result_human_wins);
                        finish.setImageResource(R.drawable.defuser_hud_csgoa);
    					mHumanCounter++;
    					mHumanCount.setText(Integer.toString(mHumanCounter));
    					mGameOver = true;
    				}
    				else
    				{
    					mInfoTextView.setText(R.string.result_android_wins);
    					mAndroidCounter++;
						finish.setImageResource(R.drawable.bang);
    					mAndroidCount.setText(Integer.toString(mAndroidCounter));
    					mGameOver = true;
    				}
    			}
    		}
    	}
    }
 
    private void setMove(int player, int location)
    {
    	mGame.setMove(player, location);
    	mBoardButtons[location].setEnabled(false);
    	mBoardButtons[location].setImageResource(player);

    }
}