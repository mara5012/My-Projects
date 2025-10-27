import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./Main_Page";
import LoginPage from "./Login_Page";
import SearchResults from "./Search_Page";
import GenrePage from "./Genre_Page";
import BookDetails from "./Book_Page";
import FilteredBooksPage from "./Filtered_Page";
import Dashboard from "./Dashboard_Page";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/users/login" element={<LoginPage />} />
        <Route path="/books/search" element={<SearchResults />} />
          <Route path="/books/genre/:genreName" element={<GenrePage />} />
          <Route path="/books/:id" element={<BookDetails />} />
          <Route path="/books/filtered" element={<FilteredBooksPage />} />
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </Router>
  );
}

export default App;
