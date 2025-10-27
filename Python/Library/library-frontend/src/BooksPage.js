import React, { useState } from 'react';

function BooksPage({ books }) {
  // Number of books per page options
  const [booksPerPage, setBooksPerPage] = useState(10);

  // Display style options: 'grid' or 'list'
  const [viewStyle, setViewStyle] = useState('grid');

  // Pagination (optional)
  const [currentPage, setCurrentPage] = useState(1);

  // Calculate books to show for current page
  const startIndex = (currentPage - 1) * booksPerPage;
  const displayedBooks = books.slice(startIndex, startIndex + booksPerPage);

  return (
    <div>
      {/* Filter Controls */}
      <div style={{ marginBottom: '20px' }}>
        <label>
          Books per page:
          <select
            value={booksPerPage}
            onChange={e => {
              setBooksPerPage(Number(e.target.value));
              setCurrentPage(1); // reset page when books per page changes
            }}
          >
            {[5, 10, 20, 50].map(num => (
              <option key={num} value={num}>{num}</option>
            ))}
          </select>
        </label>

        <label style={{ marginLeft: '20px' }}>
          View style:
          <select
            value={viewStyle}
            onChange={e => setViewStyle(e.target.value)}
          >
            <option value="grid">Grid</option>
            <option value="list">List</option>
          </select>
        </label>
      </div>

      {/* Books display */}
      <div
        style={{
          display: viewStyle === 'grid' ? 'grid' : 'block',
          gridTemplateColumns: viewStyle === 'grid' ? 'repeat(auto-fill, minmax(150px, 1fr))' : 'none',
          gap: viewStyle === 'grid' ? '20px' : '0',
        }}
      >
        {displayedBooks.map(book => (
          <div
            key={book.id}
            style={{
              border: '1px solid #ccc',
              padding: '10px',
              display: viewStyle === 'list' ? 'flex' : 'block',
              gap: viewStyle === 'list' ? '10px' : '0',
            }}
          >
            <img
              src={book.image}
              alt={book.title}
              style={{
                width: viewStyle === 'grid' ? '100%' : '80px',
                height: 'auto',
                marginRight: viewStyle === 'list' ? '10px' : '0',
              }}
            />
            <div>
              <h3>{book.title}</h3>
              <p>{book.author}</p>
            </div>
          </div>
        ))}
      </div>

      {/* Pagination controls (optional) */}
      <div style={{ marginTop: '20px' }}>
        <button
          disabled={currentPage === 1}
          onClick={() => setCurrentPage(p => p - 1)}
        >
          Previous
        </button>
        <span style={{ margin: '0 10px' }}>
          Page {currentPage} of {Math.ceil(books.length / booksPerPage)}
        </span>
        <button
          disabled={currentPage === Math.ceil(books.length / booksPerPage)}
          onClick={() => setCurrentPage(p => p + 1)}
        >
          Next
        </button>
      </div>
    </div>
  );
}

export default BooksPage;
