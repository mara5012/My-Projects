import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import logo from "./images/logo.png";
import axios from "axios";

// All book images
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
import {FacebookIcon, InstagramIcon, LinkedInIcon, TwitterIcon} from "./SocialIcons";

// Book data
const allBooks = [
  { id: 1, title: "Pride and Prejudice", author: "Jane Austen", image: book },
  { id: 2, title: "The Little Prince", author: "Antoine de Saint-Exupéry", image: book2 },
  { id: 3, title: "Lord of the Rings", author: "J.R.R. Tolkien", image: book3 },
  { id: 4, title: "Crime and Punishment", author: "Fyodor Dostoevsky", image: book4 },
  { id: 5, title: "Gone Girl", author: "Gillian Flynn", image: book5 },
  { id: 6, title: "Dracula", author: "Bram Stoker", image: book6 },
  { id: 7, title: "A Game of Thrones", author: "George R.R Martin", image: book7 },
  { id: 8, title: "Becoming", author: "Michele Obama", image: book8 },
  { id: 9, title: "The Notebook", author: "Nicholas Sparks", image: book9 },
  { id: 10, title: "Matilda", author: "Roald Dahl", image: book10 },
  { id: 11, title: "Harry Potter and the Philosopher's Stone", author: "J.K. Rowling", image: book11 },
  { id: 12, title: "Sherlock Holmes: The complete Novels and Stories", author: "Arthur Conan Doyle", image: book12 },
  { id: 13, title: "The Silent Patient", author: "Alex Michaelides", image: book13 },
  { id: 14, title: "Frankenstein", author: "Mary Shelley", image: book14 },
  { id: 15, title: "The Haunting of Hill House", author: "Shirley Jackson", image: book15 },
  { id: 16, title: "Misery", author: "Stephen King", image: book16 },
  { id: 17, title: "The Diary of a Young Girl", author: "Anne Frank", image: book17 },
  { id: 18, title: "Me Before You", author: "Jojo Moyes", image: book18 },
  { id: 19, title: "Outlander", author: "Diana Gabaldon", image: book19 },
  { id: 20, title: "Percy Jackson & The Olympians: The Lightning Thief", author: "Rick Riordan", image: book20 },
  { id: 21, title: "The Alchemist", author: "Paulo Coelho", image: book21 },
  { id: 22, title: "Murder on the Orient Express", author: "Agatha Christie", image: book22 },
  { id: 23, title: "Death on the Nile", author: "Agatha Christie", image: book23 },
  { id: 24, title: "It", author: "Stephen King", image: book24 },
  { id: 25, title: "Sense and Sensibility", author: "Jane Austen", image: book25 },
  { id: 26, title: "The Hobbit", author: "J.R.R. Tolkien", image: book26 },
  { id: 27, title: "Dune", author: "Frank Herbert", image: book27 },
  { id: 28, title: "The Martian", author: "Andy Weir", image: book28 },
  { id: 29, title: "The Count of Monte Cristo", author: "Alexandre Dumas", image: book29 },
  { id: 30, title: "Into the Wild", author: "Jon Krakauer", image: book30 },
  { id: 31, title: "The Bourne Identity", author: "Robert Ludlum", image: book31 },
  { id: 32, title: "The Hunger Games", author: "Suzanne Collins", image: book32 },
  { id: 33, title: "Jack Reacher: Killing Floor", author: "Lee Child", image: book33 },
  { id: 34, title: "The Maze Runner", author: "James Dashner", image: book34 },
  { id: 35, title: "Treasure Island", author: "Robert Louis Stevenson", image: book35 },
  { id: 36, title: "Journey to the Center of the Earth", author: "Jules Verne", image: book36 },
  { id: 37, title: "Charlotte's Web", author: "E.B. White", image: book37 },
  { id: 38, title: "The Tale of Peter Rabbit", author: "Beatrix Potter", image: book38 },
  { id: 39, title: "Winnie-the-Pooh", author: "A.A. Milne", image: book39 },
  { id: 40, title: "The Republic", author: "Plato", image: book40 },
  { id: 41, title: "The Critique of Pure Reason", author: "Immanuel Kant", image: book41 },
  { id: 42, title: "Long Walk to Freedom", author: "Nelson Mandela", image: book42 },
  { id: 43, title: "I Am Malala", author: "Malala Yousafzai", image: book43 },
  { id: 44, title: "The Girl with the Dragon Tattoo", author: "Stieg Larsson", image: book44 },
  { id: 45, title: "The Da Vinci Code", author: "Dan Brown", image: book45 },
  { id: 46, title: "The Hitchhiker's Guide to the Galaxy", author: "Douglas Adams", image: book46 },
  { id: 47, title: "Bossypants", author: "Tina Fey", image: book47 },
  { id: 48, title: "Bridget Jones's Diary", author: "Helen Fielding", image: book48 },
  { id: 49, title: "Sapiens: A Brief History of Humankind", author: "Yuval Noah Harari", image: book49 },
  { id: 50, title: "Atomic Habits", author: "James Clear", image: book50 },
];

 const categories = [
    { name: "Action", color: "#EF4444" },
    { name: "Adventure", color: "#F97316" },
    { name: "Biography", color: "#10B981" },
    { name: "Children", color: "#3B82F6" },
    { name: "Comedy", color: "#EAB308" },
    { name: "Fantasy", color: "#8B5CF6" },
    { name: "Horror", color: "#DC2626" },
    { name: "Mystery and Crime", color: "#6B7280" },
    { name: "Non-Fiction", color: "#0EA5E9" },
    { name: "Philosophical", color: "#A855F7" },
    { name: "Romance", color: "#EC4899" },
    { name: "Science-Fiction", color: "#22D3EE" },
    { name: "Thriller", color: "#F59E0B" },
  ];


function BookDetails() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState("");
  const [book, setBook] = useState(null);
   const [query, setQuery] = useState("");

    const getBookImage = (bookId) => {
    const book = allBooks.find(b => b.id === bookId);
    return book ? book.image : book; // fallback to first book image if not found
  };


  useEffect(() => {
    axios.get(`http://localhost:5000/books/${id}`)
      .then((res) => setBook(res.data))
      .catch(() => setBook(null));
  }, [id]);

  if (!book) return <div>Loading or book not found.</div>;


  const handleSearch = (e) => {
    if (e.key === "Enter") {
      navigate(`/books/search?q=${encodeURIComponent(searchTerm)}`);
    }
  };

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
            onClick={() => navigate(`/books/search?q=${encodeURIComponent(searchTerm)}`)}
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
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            onKeyDown={handleSearch}
          />
        </div>


  <button
  onClick={() => navigate("/users/login")}
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
  Sign In
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

      {/* Thin black horizontal line under genres */}
      <div
        style={{
          width: "96%",
          margin: "0 auto",
          borderBottom: "1px solid black",
          marginTop: 8,
        }}
      ></div>

      {/* Book Detail Section */}
      <div style={{ display: "flex", gap: 40, padding: 40, maxWidth: 1000, margin: "0 auto" }}>
        <img
          src={getBookImage(book.id)} // adapt path as needed
          alt={book.title}
          style={{
            width: 240,
            height: "auto",
            objectFit: "cover",
            borderRadius: 8,
            boxShadow: "0 2px 6px rgba(0,0,0,0.15)",
          }}
        />
        <div>
          <h1 style={{ fontSize: 28, marginBottom: 8 }}>{book.title}</h1>
          <h3 style={{ color: "#666", marginBottom: 16 }}>by {book.author}</h3>
          <p><strong>Genre:</strong> {book.genre}</p>
          <p><strong>Pages:</strong> {book.nb_pag}</p>
          <p><strong>Published:</strong> {book.year}</p>
          <p><strong>Copies:</strong> {book.nb_of_books}</p>
          <p><strong>Borrowed:</strong> {book.nb_of_books_borrowed}</p>
          <p><strong>Total Borrows:</strong> {book.nb_of_borrows}</p>
          <p><strong>State:</strong> {book.state}</p>
          <p style={{ marginTop: 20 }}>
            <strong>Summary:</strong> {book.summary || "No summary available."}
          </p>
        </div>
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
}

export default BookDetails;
