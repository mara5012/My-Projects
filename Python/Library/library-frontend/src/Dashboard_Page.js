import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import { FacebookIcon, InstagramIcon, TwitterIcon, LinkedInIcon } from "./SocialIcons";
import logo from "./images/logo.png";
import banner from "./images/banner.png";
import book from "./images/pride_and_prejudice.png";
import book2 from "./images/the_little_prince.png";
import book3 from "./images/lord_of_the_rings.png";
import book4 from "./images/crime_and_punishment.png";
import book5 from "./images/gone_girl.png";
import book6 from "./images/dracula.png";
import book7 from "./images/a_game_of_thrones.png";
import book8 from "./images/becoming.png";
import book9 from "./images/the_notebook.png";
import book10 from "./images/matilda.png";
import book11 from "./images/harry_potter_and_the_philosopher's_stone.png";
import book12 from "./images/sherlock_holmes.png";
import book13 from "./images/the_silent_patient.png";
import book14 from "./images/frankestein.png";
import book15 from "./images/the_haunting_of_hill_house.png";
import book16 from "./images/misery.png";
import book17 from "./images/the_diary_of_a_young_girl.png";
import book18 from "./images/me_before_you.png";
import book19 from "./images/outlander.png";
import book20 from "./images/percy_jackson.png";
import book21 from "./images/the_alchemist.png";
import book22 from "./images/murder_on_the_orient_express.png";
import book23 from "./images/death_on_the_nile.png";
import book24 from "./images/it.png";
import book25 from "./images/sense_and_sensibility.png";
import book26 from "./images/the_hobbit.png";
import book27 from "./images/dune.png";
import book28 from "./images/the_martian.png";
import book29 from "./images/the_count_of_monte_cristo.png";
import book30 from "./images/into_the_wild.png";
import book31 from "./images/the_bourne_identity.png";
import book32 from "./images/the_hunger_games.png";
import book33 from "./images/jack_reacher_killing_floor.png";
import book34 from "./images/the_maze_runner.png";
import book35 from "./images/treasure_island.png";
import book36 from "./images/journey_to_the_center_of_the_earth.png";
import book37 from "./images/charlotte's_web.png";
import book38 from "./images/the_tale_of_peter_rabbit.png";
import book39 from "./images/winne-the-pooh.png";
import book40 from "./images/the_republic.png";
import book41 from "./images/the_critique_of_pure_reason.png";
import book42 from "./images/long_walk_to_freedom.png";
import book43 from "./images/i_am_malala.png";
import book44 from "./images/the_girl_with_the_dragon_tattoo.png";
import book45 from "./images/the_davinci_code.png";
import book46 from "./images/the_hitchhiker's.png";
import book47 from "./images/bossypants.png";
import book48 from "./images/bridget_jones's_diary.png";
import book49 from "./images/sapiens.png";
import book50 from "./images/atomic_habits.png";
import axios from "axios";

export default function Dashboard() {

    const books = [
        {id: 1, title: "Pride and Prejudice", author: "Jane Austen", image: book},
        {id: 2, title: "The Little Prince", author: "Antoine de Saint-Exupéry", image: book2},
        {id: 3, title: "Lord of the Rings", author: "J.R.R. Tolkien", image: book3},
        {id: 4, title: "Crime and Punishment", author: "Fyodor Dostoevsky", image: book4},
        {id: 5, title: "Gone Girl", author: "Gillian Flynn", image: book5},
        {id: 6, title: "Dracula", author: "Bram Stoker", image: book6},
        {id: 7, title: "A Game of Thrones", author: "George R.R Martin", image: book7},
        {id: 8, title: "Becoming", author: "Michele Obama", image: book8},
        {id: 9, title: "The Notebook", author: "Nicholas Sparks", image: book9},
        {id: 10, title: "Matilda", author: "Roald Dahl", image: book10},
        {id: 11, title: "Harry Potter and the Philosopher's Stone", author: "J.K. Rowling", image: book11},
        {
            id: 12,
            title: "Sherlock Holmes: The complete Novels and Stories",
            author: "Arthur Conan Doyle",
            image: book12
        },
        {id: 13, title: "The Silent Patient", author: "Alex Michaelides", image: book13},
        {id: 14, title: "Frankenstein", author: "Mary Shelley", image: book14},
        {id: 15, title: "The Haunting of Hill House", author: "Shirley Jackson", image: book15},
        {id: 16, title: "Misery", author: "Stephen King", image: book16},
        {id: 17, title: "The Diary of a Young Girl", author: "Anne Frank", image: book17},
        {id: 18, title: "Me Before You", author: "Jojo Moyes", image: book18},
        {id: 19, title: "Outlander", author: "Diana Gabaldon", image: book19},
        {id: 20, title: "Percy Jackson & The Olympians: The Lightning Thief", author: "Rick Riordan", image: book20},
        {id: 21, title: "The Alchemist", author: "Paulo Coelho", image: book21},
        {id: 22, title: "Murder on the Orient Express", author: "Agatha Christie", image: book22},
        {id: 23, title: "Death on the Nile", author: "Agatha Christie", image: book23},
        {id: 24, title: "It", author: "Stephen King", image: book24},
        {id: 25, title: "Sense and Sensibility", author: "Jane Austen", image: book25},
        {id: 26, title: "The Hobbit", author: "J.R.R. Tolkien", image: book26},
        {id: 27, title: "Dune", author: "Frank Herbert", image: book27},
        {id: 28, title: "The Martian", author: "Andy Weir", image: book28},
        {id: 29, title: "The Count of Monte Cristo", author: "Alexandre Dumas", image: book29},
        {id: 30, title: "Into the Wild", author: "Jon Krakauer", image: book30},
        {id: 31, title: "The Bourne Identity", author: "Robert Ludlum", image: book31},
        {id: 32, title: "The Hunger Games", author: "Suzanne Collins", image: book32},
        {id: 33, title: "Jack Reacher: Killing Floor", author: "Lee Child", image: book33},
        {id: 34, title: "The Maze Runner", author: "James Dashner", image: book34},
        {id: 35, title: "Treasure Island", author: "Robert Louis Stevenson", image: book35},
        {id: 36, title: "Journey to the Center of the Earth", author: "Jules Verne", image: book36},
        {id: 37, title: "Charlotte's Web", author: "E.B. White", image: book37},
        {id: 38, title: "The Tale of Peter Rabbit", author: "Beatrix Potter", image: book38},
        {id: 39, title: "Winnie-the-Pooh", author: "A.A. Milne", image: book39},
        {id: 40, title: "The Republic", author: "Plato", image: book40},
        {id: 41, title: "The Critique of Pure Reason", author: "Immanuel Kant", image: book41},
        {id: 42, title: "Long Walk to Freedom", author: "Nelson Mandela", image: book42},
        {id: 43, title: "I Am Malala", author: "Malala Yousafzai", image: book43},
        {id: 44, title: "The Girl with the Dragon Tattoo", author: "Stieg Larsson", image: book44},
        {id: 45, title: "The Da Vinci Code", author: "Dan Brown", image: book45},
        {id: 46, title: "The Hitchhiker's Guide to the Galaxy", author: "Douglas Adams", image: book46},
        {id: 47, title: "Bossypants", author: "Tina Fey", image: book47},
        {id: 48, title: "Bridget Jones's Diary", author: "Helen Fielding", image: book48},
        {id: 49, title: "Sapiens: A Brief History of Humankind", author: "Yuval Noah Harari", image: book49},
        {id: 50, title: "Atomic Habits", author: "James Clear", image: book50},
    ];

    const [topBorrowedBooks, setTopBorrowedBooks] = useState([]);
    const [booksPerPage, setBooksPerPage] = useState(10);
    const [viewStyle, setViewStyle] = useState("grid");
    const [currentPage, setCurrentPage] = useState(1);

    const [allBooks, setAllBooks] = useState([]);
const [displayedBooks1, setDisplayedBooks1] = useState([]);

    // Calculate indexes for pagination
    const startIndex = (currentPage - 1) * booksPerPage;
    const endIndex = startIndex + booksPerPage;
    const displayedBooks = books.slice(startIndex, endIndex);

    useEffect(() => {
        const fetchBooks = async () => {
            const response = await fetch("http://localhost:5000/books");
            const data = await response.json();
            setAllBooks(data);
            setDisplayedBooks1(data); // show all initially
        };
        fetchBooks();
    }, []);
    const categories = [
        {name: "Action", color: "#EF4444"},
        {name: "Adventure", color: "#F97316"},
        {name: "Biography", color: "#10B981"},
        {name: "Children", color: "#3B82F6"},
        {name: "Comedy", color: "#EAB308"},
        {name: "Fantasy", color: "#8B5CF6"},
        {name: "Horror", color: "#DC2626"},
        {name: "Mystery and Crime", color: "#6B7280"},
        {name: "Non-Fiction", color: "#0EA5E9"},
        {name: "Philosophical", color: "#A855F7"},
        {name: "Romance", color: "#EC4899"},
        {name: "Science-Fiction", color: "#22D3EE"},
        {name: "Thriller", color: "#F59E0B"},
    ];


    const totalPages = Math.ceil(books.length / booksPerPage);
    const navigate = useNavigate()


    const [query, setQuery] = useState("");
    const [results, setResults] = useState([]);

    const handleSearch = (e) => {
        if (e.key === "Enter" && query.trim()) {
            navigate(`/books/search?q=${encodeURIComponent(query.trim())}`);
        }
    };

    // Helper function to get book image by ID
    const getBookImage = (bookId) => {
        const book = books.find(b => b.id === bookId);
        return book ? book.image : book; // fallback to first book image if not found
    };

    useEffect(() => {
        console.log("Starting API call to fetch top borrowed books...");

        fetch("http://localhost:5000/books/top-borrowed")
            .then((res) => {
                console.log("Response received:", res);
                console.log("Response status:", res.status);
                console.log("Response ok:", res.ok);

                if (!res.ok) {
                    throw new Error(`HTTP error! status: ${res.status}`);
                }
                return res.json();
            })
            .then((data) => {
                console.log("Raw fetched data:", data);
                console.log("Data type:", typeof data);
                console.log("Is array:", Array.isArray(data));
                console.log("Data length:", data?.length);

                if (Array.isArray(data)) {
                    setTopBorrowedBooks(data);
                    console.log("Successfully set topBorrowedBooks with", data.length, "items");
                } else {
                    console.error("Data is not an array:", data);
                    setTopBorrowedBooks([]);
                }
            })
            .catch((err) => {
                console.error("Error fetching top borrowed books:", err);
                console.error("Error name:", err.name);
                console.error("Error message:", err.message);
                // Set empty array on error to prevent undefined issues
                setTopBorrowedBooks([]);
            });
    }, []);

    console.log("Top borrowed books:", topBorrowedBooks);
    console.log("Number of books:", topBorrowedBooks.length);

    const handleBorrowBook = () => {
  alert("Borrow Book clicked");
};

const handleReturnBook = () => {
  alert("Return Book clicked");
};

const handleBuyCard = () => {
  alert("Buy Card clicked");
};

const handleAddBook = () => {
  alert("Add Book clicked");
};

const handleModifyBook = () => {
  alert("Modify Book clicked");
};


    const [topBooksByGenre, setTopBooksByGenre] = useState({});

    useEffect(() => {
        axios.get("http://localhost:5000/books/top-by-all-genres")
            .then((res) => setTopBooksByGenre(res.data))
            .catch((err) => {
                console.error("Error fetching top books by genre:", err);
                setTopBooksByGenre({});
            });
    }, []);
    const genreColors = {
        "Action": "#EF4444",
        "Adventure": "#F97316",
        "Biography": "#10B981",
        "Children": "#3B82F6",
        "Comedy": "#EAB308",
        "Fantasy": "#8B5CF6",
        "Horror": "#DC2626",
        "Mystery and Crime": "#6B7280",
        "Non-Fiction": "#0EA5E9",
        "Philosophical": "#A855F7",
        "Romance": "#EC4899",
        "Science-Fiction": "#22D3EE",
        "Thriller": "#F59E0B",
    };

    const buttonStyle = {
  padding: "10px 20px",
  border: "none",
  borderRadius: "8px",
  backgroundColor: "#6A1EBB",
  color: "white",
  fontWeight: "600",
  cursor: "pointer",
  transition: "0.2s",
};

<div style={{ display: "flex", gap: "12px", flexWrap: "wrap", margin: "20px 0" }}>
  <button style={buttonStyle} onClick={handleBorrowBook}>Borrow Book</button>
  <button style={buttonStyle} onClick={handleReturnBook}>Return Book</button>
  <button style={buttonStyle} onClick={handleBuyCard}>Buy Card</button>
  <button style={buttonStyle} onClick={handleAddBook}>Add Book</button>
  <button style={buttonStyle} onClick={handleModifyBook}>Modify Book</button>
</div>


    return (
    <div className="min-h-screen bg-white">
      {/* Top Purple Bar */}
      <div
        style={{
          backgroundColor: "#E7C6FF",
          color: "#333333",
          height: "32px",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          padding: "0 12px",
          fontSize: "16px",
        }}
      >
        <span style={{ fontWeight: "bold" }}>
          Classic Elegance, Modern Tales
        </span>
      </div>

      {/* Header */}
      <header
        className="relative border-b"
        style={{
          height: 80,
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          position: "relative",
          padding: "0 16px",
          marginTop: 12,
        }}
      >
        {/* Logo aligned top-left */}
        <div
  style={{
    position: "absolute",
    left: 16,
    top: "50%",
    transform: "translateY(-50%)",
  }}
>
  <a href="/">
    <img src={logo} alt="Logo" style={{ height: 110, width: "auto" }} />
  </a>
</div>


     {/* Centered search bar with icon */}
        <div style={{ maxWidth: 600, width: "100%", position: "relative" }}>
          <svg
        xmlns="http://www.w3.org/2000/svg"
        onClick={() => navigate(`/books/search?q=${encodeURIComponent(query)}`)}
        style={{
          position: "absolute",
          right: 12,
          top: "50%",
          transform: "translateY(-50%)",
          width: 20,
          height: 20,
          stroke: "#7C3AED",
          strokeWidth: 2,
          cursor: "pointer",
        }}
        viewBox="0 0 24 24"
        fill="none"
      >
        <circle cx="11" cy="11" r="7" />
        <line x1="21" y1="21" x2="16.65" y2="16.65" />
      </svg>


          <input
            type="search"
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            onKeyDown={handleSearch}
            placeholder="Search books, authors, genre..."
            style={{
              width: "100%",
              padding: "8px 40px 8px 12px",
              fontSize: 16,
              borderRadius: 6,
              border: "1px solid #ccc",
              outline: "none",
              height: 36,
              boxSizing: "border-box",
            }}
          />
        </div>


  <button
  onClick={() => navigate("/")}
  style={{
    position: "absolute",
    right: 16,
    top: "50%",
    transform: "translateY(-50%)",
    backgroundColor: "#4F46E5",
    color: "white",
    border: "none",
    borderRadius: 6,
    padding: "8px 16px",
    fontSize: 16,
    fontWeight: "bold",
    cursor: "pointer",
    whiteSpace: "nowrap",
    height: 36,
    display: "flex",
    alignItems: "center",
    gap: 8,
  }}
>
  {/* Person icon SVG */}
  <svg
    xmlns="http://www.w3.org/2000/svg"
    fill="none"
    viewBox="0 0 24 24"
    strokeWidth="2"
    stroke="white"
    style={{ width: 20, height: 20 }}
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      d="M16 7a4 4 0 11-8 0 4 4 0 018 0z"
    />
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      d="M12 14c-4 0-7 2-7 4v1h14v-1c0-2-3-4-7-4z"
    />
  </svg>
  Log Out
</button>
        </header>

        {/* Category Buttons */}
       <div
      style={{
        display: "flex",
        flexWrap: "wrap",
        gap: "12px",
        justifyContent: "center",
        padding: "24px 16px",
      }}
    >
      {categories.map((category) => (
        <button
          key={category.name}
          style={{
            backgroundColor: "#FFFFFF",
            color: category.color,
            border: `2px solid ${category.color}`,
            borderRadius: "20px",
            padding: "8px 16px",
            fontSize: "14px",
            fontWeight: "500",
            cursor: "pointer",
            transition: "all 0.2s",
          }}
          onMouseOver={(e) => {
            e.target.style.backgroundColor = category.color;
            e.target.style.color = "#FFFFFF";
          }}
          onMouseOut={(e) => {
            e.target.style.backgroundColor = "#FFFFFF";
            e.target.style.color = category.color;
          }}
          onClick={() => navigate(`/books/genre/${encodeURIComponent(category.name)}`)}
        >
          {category.name}
        </button>
      ))}
    </div>
        
<div
  style={{
    display: "flex",
    flexWrap: "wrap",
    justifyContent: "center",
    gap: "16px",
    padding: "32px 24px",
    maxWidth: "1000px",
    margin: "0 auto",
  }}
>
  {[
    { label: "Borrow Book", color: "#6D9F71", onClick: handleBorrowBook },
    { label: "Return Book", color: "#FD9A4D", onClick: handleReturnBook },
    { label: "Buy Card", color: "#6D97F1", onClick: handleBuyCard },
    { label: "Add Book", color: "#AC88E8", onClick: handleAddBook },
    { label: "Modify Book", color: "#E27396", onClick: handleModifyBook },
  ].map(({ label, color, onClick }) => (
    <button
      key={label}
      style={{
        flex: "1 1 300px",
        backgroundColor: color,
        color: "#fff",
        border: `2px solid ${color}`,
        borderRadius: "24px",
        padding: "14px 24px",
        fontSize: "16px",
        fontWeight: "600",
        cursor: "pointer",
        transition: "all 0.3s ease",
        minWidth: "250px",
        maxWidth: "100%",
      }}
      onMouseOver={(e) => {
        e.target.style.filter = "brightness(0.9)";
      }}
      onMouseOut={(e) => {
        e.target.style.filter = "brightness(1)";
      }}
      onClick={onClick}
    >
      {label}
    </button>
  ))}
</div>



        <h2
  style={{
    maxWidth: 1000,
    margin: "40px auto 12px",
    textAlign: "center",
    color: "#4B0082",
    fontWeight: "700",
    fontSize: 28,
    userSelect: "none",
    textShadow: "1px 1px 2px rgba(106,17,203,0.5)",
  }}
>
  All of our books
</h2>


      {/* Filter Section */}
<div
  style={{
    maxWidth: 1000,
    margin: "20px auto",
    display: "flex",
    gap: 30,
    justifyContent: "center",
    padding: "16px 24px",
    background: "transparent",
    color: "#6a11cb",
    fontWeight: "600",
    userSelect: "none",
  }}
>
  <label style={{ display: "flex", alignItems: "center", gap: 12 }}>
    Books per page:
    <select
      value={booksPerPage}
      onChange={(e) => {
        setBooksPerPage(Number(e.target.value));
        setCurrentPage(1);
      }}
      style={{
        padding: "8px 14px",
        borderRadius: 12,
        border: "none",
        fontWeight: "600",
        fontSize: 16,
        cursor: "pointer",
        outline: "none",
        background: "rgba(106, 17, 203, 0.15)",
        color: "#6a11cb",
        boxShadow:
          "inset 2px 2px 6px rgba(255,255,255,0.3), inset -2px -2px 6px rgba(0,0,0,0.2)",
        transition: "all 0.25s ease",
      }}
      onMouseEnter={(e) => {
        e.target.style.background = "rgba(106, 17, 203, 0.3)";
        e.target.style.boxShadow =
          "inset 3px 3px 8px rgba(255,255,255,0.5), inset -3px -3px 8px rgba(0,0,0,0.3)";
      }}
      onMouseLeave={(e) => {
        e.target.style.background = "rgba(106, 17, 203, 0.15)";
        e.target.style.boxShadow =
          "inset 2px 2px 6px rgba(255,255,255,0.3), inset -2px -2px 6px rgba(0,0,0,0.2)";
      }}
    >
      {[5, 10, 20, 50].map((num) => (
        <option key={num} value={num} style={{ color: "#6a11cb" }}>
          {num}
        </option>
      ))}
    </select>
  </label>

  <label style={{ display: "flex", alignItems: "center", gap: 12 }}>
    View style:
    <select
      value={viewStyle}
      onChange={(e) => setViewStyle(e.target.value)}
      style={{
        padding: "8px 14px",
        borderRadius: 12,
        border: "none",
        fontWeight: "600",
        fontSize: 16,
        cursor: "pointer",
        outline: "none",
        background: "rgba(106, 17, 203, 0.15)",
        color: "#6a11cb",
        boxShadow:
          "inset 2px 2px 6px rgba(255,255,255,0.3), inset -2px -2px 6px rgba(0,0,0,0.2)",
        transition: "all 0.25s ease",
      }}
      onMouseEnter={(e) => {
        e.target.style.background = "rgba(106, 17, 203, 0.3)";
        e.target.style.boxShadow =
          "inset 3px 3px 8px rgba(255,255,255,0.5), inset -3px -3px 8px rgba(0,0,0,0.3)";
      }}
      onMouseLeave={(e) => {
        e.target.style.background = "rgba(106, 17, 203, 0.15)";
        e.target.style.boxShadow =
          "inset 2px 2px 6px rgba(255,255,255,0.3), inset -2px -2px 6px rgba(0,0,0,0.2)";
      }}
    >
      <option value="grid" style={{ color: "#6a11cb" }}>
        Grid
      </option>
      <option value="list" style={{ color: "#6a11cb" }}>
        List
      </option>
    </select>
  </label>
</div>

    {/* Books container */}
  <main
    style={{
        display: "grid",
        gridTemplateColumns: "repeat(4, 1fr)",
        flexDirection: viewStyle === "list" ? "column" : undefined,
        gap: "35px",
        padding: "16px",
        maxWidth: 1000,
        margin: "25px auto 48px",
      }}
  >
    {displayedBooks.length > 0 ? (
      displayedBooks.map((book) => (
        <div
  key={book.id}
  onClick={() => navigate(`/books/${book.id}`)}
  style={{
    cursor: "pointer",
    border: "0.5px solid #ddd",
    borderRadius: 6,
    backgroundColor: "#fafafa",
    padding: 12,
    display: "flex",
    flexDirection: viewStyle === "list" ? "row" : "column",
    alignItems: viewStyle === "list" ? "center" : "flex-start",
    gap: viewStyle === "list" ? "16px" : 0,
    width: viewStyle === "list" ? "1000px" : "auto",
    boxSizing: "border-box",
    height: viewStyle === "list" ? "140px" : "auto", // shorter height
  }}
>
  <img
    src={book.image || getBookImage(book.id)}
    onError={(e) => {
      e.target.onerror = null;
      e.target.src = "https://via.placeholder.com/150x240?text=No+Image";
    }}
    alt={book.title}
    style={{
      width: viewStyle === "list" ? 120 : "100%", // slightly narrower image
      height: viewStyle === "list" ? 120 : 285,  // shorter image height
      objectFit: "cover",
      borderRadius: 4,
      marginBottom: viewStyle === "grid" ? 12 : 0,
      flexShrink: 0,
    }}
  />
  <div
    style={{
      textAlign: viewStyle === "grid" ? "center" : "left",
      flex: 1,
      overflow: "hidden",
    }}
  >
    <h3
      style={{
        fontSize: 16,
        margin: "0 0 6px 0",
        whiteSpace: "nowrap",
        textOverflow: "ellipsis",
        overflow: "hidden",
      }}
    >
      {book.title}
    </h3>
    <p
      style={{
        margin: 0,
        fontSize: 14,
        fontWeight: "normal",
        color: "#555",
        whiteSpace: "nowrap",
        textOverflow: "ellipsis",
        overflow: "hidden",
      }}
    >
      {book.author}
    </p>
  </div>
</div>
      ))
    ) : (
      <p style={{ padding: 24 }}>No books found.</p>
    )}
  </main>


      {/* Pagination Buttons */}
<div style={{ textAlign: "center", marginBottom: 30 }}>
  <button
    disabled={currentPage === 1}
    onClick={() => setCurrentPage(currentPage - 1)}
    style={{
      padding: "10px 20px",
      marginRight: 12,
      borderRadius: 12,
      border: "none",
      background: currentPage === 1
        ? "linear-gradient(145deg, #d3d3d3, #e9e9e9)"
        : "linear-gradient(145deg, #6a11cb, #2575fc)",
      color: currentPage === 1 ? "#aaa" : "#fff",
      cursor: currentPage === 1 ? "not-allowed" : "pointer",
      fontWeight: "700",
      fontSize: 16,
      boxShadow: currentPage === 1
        ? "inset 2px 2px 5px #b1b1b1, inset -2px -2px 5px #ffffff"
        : "4px 4px 10px rgba(38, 115, 254, 0.6), -4px -4px 10px rgba(106, 17, 203, 0.6)",
      transition: "all 0.25s ease",
      transformOrigin: "center",
      userSelect: "none",
    }}
    onMouseEnter={e => {
      if (currentPage !== 1) {
        e.target.style.filter = "brightness(1.15)";
        e.target.style.transform = "scale(1.05)";
        e.target.style.boxShadow = "6px 6px 15px rgba(38, 115, 254, 0.9), -6px -6px 15px rgba(106, 17, 203, 0.9)";
      }
    }}
    onMouseLeave={e => {
      if (currentPage !== 1) {
        e.target.style.filter = "brightness(1)";
        e.target.style.transform = "scale(1)";
        e.target.style.boxShadow = "4px 4px 10px rgba(38, 115, 254, 0.6), -4px -4px 10px rgba(106, 17, 203, 0.6)";
      }
    }}
    onMouseDown={e => {
      if (currentPage !== 1) {
        e.target.style.transform = "scale(0.95)";
      }
    }}
    onMouseUp={e => {
      if (currentPage !== 1) {
        e.target.style.transform = "scale(1.05)";
      }
    }}
  >
    Previous
  </button>

  <span style={{ margin: "0 20px", fontWeight: "700", fontSize: 18, color: "#333" }}>
    Page {currentPage} of {totalPages}
  </span>

  <button
    disabled={currentPage === totalPages}
    onClick={() => setCurrentPage(currentPage + 1)}
    style={{
      padding: "10px 20px",
      marginLeft: 12,
      borderRadius: 12,
      border: "none",
      background: currentPage === totalPages
        ? "linear-gradient(145deg, #d3d3d3, #e9e9e9)"
        : "linear-gradient(145deg, #6a11cb, #2575fc)",
      color: currentPage === totalPages ? "#aaa" : "#fff",
      cursor: currentPage === totalPages ? "not-allowed" : "pointer",
      fontWeight: "700",
      fontSize: 16,
      boxShadow: currentPage === totalPages
        ? "inset 2px 2px 5px #b1b1b1, inset -2px -2px 5px #ffffff"
        : "4px 4px 10px rgba(38, 115, 254, 0.6), -4px -4px 10px rgba(106, 17, 203, 0.6)",
      transition: "all 0.25s ease",
      transformOrigin: "center",
      userSelect: "none",
    }}
    onMouseEnter={e => {
      if (currentPage !== totalPages) {
        e.target.style.filter = "brightness(1.15)";
        e.target.style.transform = "scale(1.05)";
        e.target.style.boxShadow = "6px 6px 15px rgba(38, 115, 254, 0.9), -6px -6px 15px rgba(106, 17, 203, 0.9)";
      }
    }}
    onMouseLeave={e => {
      if (currentPage !== totalPages) {
        e.target.style.filter = "brightness(1)";
        e.target.style.transform = "scale(1)";
        e.target.style.boxShadow = "4px 4px 10px rgba(38, 115, 254, 0.6), -4px -4px 10px rgba(106, 17, 203, 0.6)";
      }
    }}
    onMouseDown={e => {
      if (currentPage !== totalPages) {
        e.target.style.transform = "scale(0.95)";
      }
    }}
    onMouseUp={e => {
      if (currentPage !== totalPages) {
        e.target.style.transform = "scale(1.05)";
      }
    }}
  >
    Next
  </button>
</div>

 <footer
      style={{
        backgroundColor: "#3D264C",
        color: "#fff",
        padding: "40px 20px",
        marginTop: "40px",
        fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
      }}
    >
      <div
        style={{
          maxWidth: "1200px",
          margin: "0 auto",
          display: "flex",
          flexWrap: "wrap",
          justifyContent: "space-between",
          gap: "30px",
        }}
      >
        {/* About */}
        <div style={{ flex: "1 1 250px", minWidth: "200px" }}>
          <h3 style={{ marginBottom: "15px" }}>About Bookish</h3>
          <p style={{ lineHeight: 1.6, fontSize: "14px" }}>
            Bookish is your online destination to discover, borrow, and explore
            thousands of books from all genres. Dive into your next adventure today!
          </p>
        </div>

        {/* Quick Links */}
        <div style={{ flex: "1 1 150px", minWidth: "150px" }}>
          <h3 style={{ marginBottom: "15px" }}>Quick Links</h3>
          <ul style={{ listStyle: "none", padding: 0, fontSize: "14px" }}>
            <li>
              <a
                href="/"
                style={{ color: "#fff", textDecoration: "none" }}
                onMouseOver={(e) => (e.target.style.color = "#6A1EBB")}
                onMouseOut={(e) => (e.target.style.color = "#fff")}
              >
                Home
              </a>
            </li>
            <li>
              <a
                href="/catalog"
                style={{ color: "#fff", textDecoration: "none" }}
                onMouseOver={(e) => (e.target.style.color = "#6A1EBB")}
                onMouseOut={(e) => (e.target.style.color = "#fff")}
              >
                Catalog
              </a>
            </li>
            <li>
              <a
                href="/about"
                style={{ color: "#fff", textDecoration: "none" }}
                onMouseOver={(e) => (e.target.style.color = "#6A1EBB")}
                onMouseOut={(e) => (e.target.style.color = "#fff")}
              >
                About Us
              </a>
            </li>
            <li>
              <a
                href="/contact"
                style={{ color: "#fff", textDecoration: "none" }}
                onMouseOver={(e) => (e.target.style.color = "#6A1EBB")}
                onMouseOut={(e) => (e.target.style.color = "#fff")}
              >
                Contact
              </a>
            </li>
          </ul>
        </div>

        {/* Contact */}
        <div style={{ flex: "1 1 250px", minWidth: "200px" }}>
          <h3 style={{ marginBottom: "15px" }}>Contact Us</h3>
          <p style={{ fontSize: "14px", lineHeight: 1.6 }}>
            Email: <a href="mailto:support@Bookish.com" style={{ color: "#E0B6E4" }}>support@Bookish.com</a>
            <br />
            Phone: <a href="tel:+1234567890" style={{ color: "#E0B6E4" }}>+1 (234) 567-890</a>
            <br />
            Address: 123 Library St, Booktown, BK 12345
          </p>
        </div>

        {/* Social Media */}
        <div style={{ display: "flex", gap: "12px", alignItems: "center" }}>
        <a href="https://www.facebook.com" target="_blank" rel="noopener noreferrer" aria-label="Facebook">
          <FacebookIcon />
        </a>
        <a href="https://www.instagram.com" target="_blank" rel="noopener noreferrer" aria-label="Instagram">
          <InstagramIcon />
        </a>
        <a href="https://www.twitter.com" target="_blank" rel="noopener noreferrer" aria-label="Twitter">
          <TwitterIcon />
        </a>
        <a href="https://www.linkedin.com" target="_blank" rel="noopener noreferrer" aria-label="LinkedIn">
          <LinkedInIcon />
        </a>
      </div>
      </div>

      <div
        style={{
          textAlign: "center",
          marginTop: "40px",
          fontSize: "13px",
          color: "#aaa",
        }}
      >
        &copy; {new Date().getFullYear()} Bookish. All rights reserved.
      </div>
    </footer>
    </div>



    );

};


