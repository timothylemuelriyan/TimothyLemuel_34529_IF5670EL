package umn.ac.id.week09_timothylemuel_34529;

inActivity extends AppCompatActivity {

private RecyclerView rv;
private MahasiswaViewModel mhsVM;
private static final int REQUEST_TAMBAH = 1;
private static final int REQUEST_EDIT = 2;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent addIntent = new Intent(MainActivity.this,
        DetilActivity.class);
        startActivityForResult(addIntent,REQUEST_TAMBAH);
        }
        });
        rv = (RecyclerView) findViewById(R.id.rvMahasiswa);
final MahasiswaListAdapter adapter = new MahasiswaListAdapter(this) {
@androidx.annotation.NonNull
@Override
public MahasiswaViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        return null;
        }
        };
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mhsVM = (MahasiswaViewModel) ViewModelProviders.of(this).get(MahasiswaViewModel.class);
        mhsVM.getDaftarMahasiswa().observe(this,
        new Observer<List<Mahasiswa>>() {
@Override
public void onChanged(List<Mahasiswa> mhss) {
        adapter.setDaftarMahasiswa(mhss);
        }
        });
        ItemTouchHelper helper = new ItemTouchHelper(
        new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |
        ItemTouchHelper.RIGHT){
private RecyclerView recyclerView;
private RecyclerView.ViewHolder viewHolder;
private RecyclerView.ViewHolder target;

@Override
public boolean onMove(@NonNull RecyclerView recyclerView,
@NonNull RecyclerView.ViewHolder viewHolder,
@NonNull RecyclerView.ViewHolder target) {
        this.recyclerView = recyclerView;
        this.viewHolder = viewHolder;
        this.target = target;
        return false;
        }
@Override
public void onSwiped(@NonNull RecyclerView.ViewHolder
        viewHolder, int direction) {
        int posisi = viewHolder.getAdapterPosition();
        Mahasiswa mhs = adapter.getMahasiswaAtPosition(posisi);
        if(direction == ItemTouchHelper.LEFT){
        Toast.makeText(MainActivity.this,
        "Mahasiswa dengan NIM = "+mhs.getNim()+
        " dihapus", Toast.LENGTH_LONG).show();
        mhsVM.delete(mhs);
        }
        else if (direction == ItemTouchHelper.RIGHT) {
        Intent editIntet = new Intent (MainActivity.this,
        DetilActivity.class);
        editIntet.putExtra("MAHASISWA", (Parcelable) mhs);
        startActivityForResult(editIntet,REQUEST_EDIT);
        }
        }
        }
        );
        helper.attachToRecyclerView(rv);
        }

private void setSupportActionBar(Toolbar toolbar) {
        }

@Override
public void onActivityResult(int reqCode, int resultCode, Intent data){
        super.onActivityResult(reqCode, resultCode, data);
        if(resultCode == RESULT_OK){
        Mahasiswa mhs = (Mahasiswa)
        data.getSerializableExtra("MAHASISWA");
        if(reqCode == REQUEST_TAMBAH ) {
        mhsVM.insert(mhs);
        }
        else if (reqCode == REQUEST_EDIT) {
        mhsVM.update(mhs);
        }
        }
        rv.getAdapter().notifyDataSetChanged();
        }
@Override
public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
        }
@Override
public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        mhsVM.deleteAll();
        return true;
        }
        return super.onOptionsItemSelected(item);