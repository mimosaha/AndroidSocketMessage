package portal.remote.superpeer.remote.com.androidsocketmessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import portal.remote.superpeer.remote.com.androidsocketmessage.model.SendMessage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SocketParser.DataCallback {

    private List<SendMessage> messages = new ArrayList<>();
    private RecyclerView recyclerView;
    private Button sendMessage;
    private EditText messageEditText;
    private Random random = new Random();
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        sendMessage = findViewById(R.id.send);
        messageEditText = findViewById(R.id.message_box);

        setUpRecyclerView();
        sendMessage.setOnClickListener(this);

        SocketParser.getInstance().setDataCallback(this).startProcess();
    }

    private void setUpRecyclerView() {

        mainAdapter = new MainAdapter(messages);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketParser.getInstance().stopProcess();
    }

    private void messageAdd(String message) {
        if (!TextUtils.isEmpty(message)) {
            setMessageData(message, MainAdapter.MESSAGE_OUT);
            SocketParser.getInstance().sendMessage(message);
            messageEditText.setText("");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send:
                String message = messageEditText.getText().toString();
                messageAdd(message);
                break;
        }
    }

    @Override
    public void processData(final String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setMessageData(data, MainAdapter.MESSAGE_IN);
            }
        });
    }

    private void setMessageData(String data, int messageType) {
        SendMessage sendMessage = new SendMessage()
                .setMessageId(System.currentTimeMillis())
                .setListMessage(data)
                .setMessageType(messageType);

        mainAdapter.addItem(sendMessage);
        recyclerView.scrollToPosition(0);
    }
}
