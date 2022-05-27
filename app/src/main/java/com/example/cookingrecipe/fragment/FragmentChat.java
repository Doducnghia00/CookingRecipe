package com.example.cookingrecipe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookingrecipe.Activity.LoginActivity;
import com.example.cookingrecipe.MainActivity;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.model.ChatMessage;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentChat extends Fragment implements View.OnClickListener{
    private EditText input;
    private FloatingActionButton btSend;
    private ListView listView;
    private ProgressBar progressBar;
    private FirebaseListAdapter<ChatMessage> adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        progressBar.setVisibility(View.VISIBLE);
        displayChatMessages();

        btSend.setOnClickListener(this);

    }

    private void displayChatMessages() {

        adapter = new FirebaseListAdapter<ChatMessage>(getActivity(),ChatMessage.class,R.layout.item_chat,FirebaseDatabase.getInstance().getReference("chat_message")) {
            @Override
            protected void populateView(View view, ChatMessage model, int position) {
                TextView messageText = view.findViewById(R.id.message_text);
                TextView messageUser = view.findViewById(R.id.message_user);
                TextView messageTime = view.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };
        progressBar.setVisibility(View.GONE);
        listView.setAdapter(adapter);
    }

    private void initView(View view) {
        input = view.findViewById(R.id.input);
        btSend = view.findViewById(R.id.btSend);
        listView = view.findViewById(R.id.listView);
        progressBar =view.findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View view) {
        if(view == btSend){
            FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
            if(input.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "Vui lòng điền nội dung tin nhắn", Toast.LENGTH_SHORT).show();
                return;
            }
            if(user == null){
                Toast.makeText(getActivity(), "Vui lòng đăng nhập để gửi tin nhắn", Toast.LENGTH_SHORT).show();
            }else{
                String name = user.getDisplayName();
                String email = user.getEmail();

                if(name != null & !name.equals("")){
                    FirebaseDatabase.getInstance()
                            .getReference("chat_message")
                            .push()
                            .setValue(new ChatMessage(input.getText().toString(),
                                    FirebaseAuth.getInstance()
                                            .getCurrentUser()
                                            .getDisplayName())
                            );
                }else{
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .push()
                            .setValue(new ChatMessage(input.getText().toString(),
                                    FirebaseAuth.getInstance()
                                            .getCurrentUser()
                                            .getEmail())
                            );
                }


                // Clear input
                input.setText("");
            }
        }
    }
}
