    package com.example.daily_new.View;

    import android.content.Context;
    import android.content.Intent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.TextView;
    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.daily_new.R;

    import java.util.List;

    public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
        private List<Book> bookList;
        private Context context;

        public BookAdapter(List<Book> bookList, Context context) {
            this.bookList = bookList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Book book = bookList.get(position);
            holder.title.setText(book.getTitle());
            holder.author.setText(book.getAuthor());
            holder.publishedYear.setText(String.valueOf(book.getPublishedYear()));

            holder.itemView.setOnClickListener(v -> {
                // 点击查看或更新书籍
                Intent intent = new Intent(context, UpdateBookActivity.class);
                intent.putExtra("bookId", book.getId());
                context.startActivity(intent);
            });

            holder.deleteButton.setOnClickListener(v -> {
                // 删除书籍
                DatabaseHelper db = new DatabaseHelper(context);
                db.deleteBook(book.getId());
                bookList.remove(position);
                notifyItemRemoved(position);
            });
        }

        @Override
        public int getItemCount() {
            return bookList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView title, author, publishedYear;
            Button deleteButton;

            public ViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.bookTitle);
                author = itemView.findViewById(R.id.bookAuthor);
                publishedYear = itemView.findViewById(R.id.bookYear);
                deleteButton = itemView.findViewById(R.id.deleteButton);
            }
        }
    }