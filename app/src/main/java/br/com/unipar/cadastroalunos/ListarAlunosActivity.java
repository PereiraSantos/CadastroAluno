package br.com.unipar.cadastroalunos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListarAlunosActivity extends AppCompatActivity {

    private ListView listView;
    private AlunoDAO dao;
    private List<Aluno> alunos;
    private List<Aluno> alunosFiltrados =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alunos);

        listView = findViewById(R.id.lista_alunos);
        dao = new AlunoDAO(this);
        alunos = dao.obterTodos();
        alunosFiltrados.addAll(alunos);
        //adaptador para mostra lista
        ArrayAdapter<Aluno> arrayAdapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1,alunosFiltrados);
        //seta arrayAdapter no xml lista_alunos
        listView.setAdapter(arrayAdapter);
    }

    //sibreescrever menu da classe AppCompatActivitiy
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater  i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);
        //pesquisa na lista de item
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        //escuta o teclado
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procurar(s);
                return false;
            }
        });
        return true;
    }

    public void procurar(String nome){
        alunosFiltrados.clear();
        for(Aluno a : alunos){
            if(a.getNome().toLowerCase().contains(nome.toLowerCase())){
                alunosFiltrados.add(a);
            }
        }
        listView.invalidateViews();
    }

    //chama xml cadastrar [MainActivity]
    public void cadastrar(MenuItem item){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //sobreecreve metodo onresume //recarega lista
    @Override
    public void onResume(){
        super.onResume();
        alunos = dao.obterTodos();
        alunosFiltrados.clear();
        alunosFiltrados.addAll(alunos);
        listView.invalidateViews();
    }
}
