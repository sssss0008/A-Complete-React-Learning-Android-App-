package com.example.data.repository

import com.example.data.model.Milestone
import com.example.data.model.ProjectLevel
import com.example.data.model.ReactProject

object ProjectData {

    val allProjects: List<ReactProject> = listOf(
        // BEGINNER PROJECTS
        ReactProject(
            id = "proj_todo",
            title = "TaskFlow Todo Laboratory",
            level = ProjectLevel.BEGINNER,
            category = "Productivity",
            estimatedHours = 3,
            summary = "Build a dynamic task management app with category filters, priority tags, localStorage persistence, and inline task editing.",
            keyFeatures = listOf(
                "Add, toggle, edit, and delete tasks",
                "Filter by All, Active, and Completed",
                "Persist state to localStorage",
                "Clear completed batch action"
            ),
            architecturalConcepts = listOf("useState array manipulation", "Controlled inputs", "useEffect localStorage sync", "Derived state filtering"),
            milestones = listOf(
                Milestone("m1", "Setup State & Input", "Create controlled input and add task handler", true),
                Milestone("m2", "Render Task List", "Map tasks with unique keys and status badges", true),
                Milestone("m3", "Implement Filters", "Filter active vs completed tasks via derived state", false),
                Milestone("m4", "Persistence", "Sync to localStorage with useEffect", false)
            ),
            starterFiles = mapOf(
                "App.jsx" to """import React, { useState, useEffect } from 'react';

export default function TodoApp() {
  const [tasks, setTasks] = useState([
    { id: 1, text: 'Learn React Fundamentals', done: true },
    { id: 2, text: 'Build TaskFlow App', done: false }
  ]);
  const [text, setText] = useState('');

  const addTask = (e) => {
    e.preventDefault();
    if (!text.trim()) return;
    setTasks([...tasks, { id: Date.now(), text, done: false }]);
    setText('');
  };

  const toggle = (id) => {
    setTasks(tasks.map(t => t.id === id ? { ...t, done: !t.done } : t));
  };

  return (
    <div className="todo-container">
      <h2>TaskFlow Studio</h2>
      <form onSubmit={addTask}>
        <input value={text} onChange={e => setText(e.target.value)} placeholder="New task..." />
        <button type="submit">Add</button>
      </form>
      <ul>
        {tasks.map(t => (
          <li key={t.id} onClick={() => toggle(t.id)} style={{ textDecoration: t.done ? 'line-through' : 'none' }}>
            {t.done ? '✓ ' : '○ '} {t.text}
          </li>
        ))}
      </ul>
    </div>
  );
}"""
            ),
            tags = listOf("useState", "useEffect", "Lists & Keys", "Forms")
        ),
        ReactProject(
            id = "proj_counter",
            title = "Pro Multi-Counter & Stopwatch",
            level = ProjectLevel.BEGINNER,
            category = "Utilities",
            estimatedHours = 2,
            summary = "A reactive counter with step increments, min/max clamps, history logs, and an integrated millisecond stopwatch with lap timer.",
            keyFeatures = listOf("Increment/decrement with custom step size", "Lap history list", "Reset with undo toast", "Keyboard arrow controls"),
            architecturalConcepts = listOf("Functional updates", "useRef for interval timers", "Cleanup functions"),
            milestones = listOf(
                Milestone("c1", "Basic Counter", "Increment/decrement state", true),
                Milestone("c2", "Step Size & Boundaries", "Configurable step and clamping", false),
                Milestone("c3", "Stopwatch Timer", "Interval timer with useRef and cleanup", false)
            ),
            starterFiles = mapOf(
                "App.jsx" to """import React, { useState } from 'react';

export default function Counter() {
  const [count, setCount] = useState(0);
  const [step, setStep] = useState(1);

  return (
    <div>
      <h2>Count: {count}</h2>
      <button onClick={() => setCount(c => c + step)}>+{step}</button>
      <button onClick={() => setCount(c => c - step)}>-{step}</button>
      <button onClick={() => setCount(0)}>Reset</button>
    </div>
  );
}"""
            ),
            tags = listOf("useState", "Events")
        ),
        ReactProject(
            id = "proj_calc",
            title = "Modern Glassmorphism Calculator",
            level = ProjectLevel.BEGINNER,
            category = "Utilities",
            estimatedHours = 3,
            summary = "A sleek, responsive calculator supporting basic arithmetic, chained operations, decimal precision, and history view.",
            keyFeatures = listOf("Operations (+, -, *, /)", "Display memory and current input", "Scientific functions toggle", "Keyboard support"),
            architecturalConcepts = listOf("State reducer logic", "Input sanitization", "Grid layout"),
            milestones = listOf(
                Milestone("cl1", "Keypad UI", "Build CSS grid buttons", true),
                Milestone("cl2", "Operand & Operator State", "Handle operand chaining", false)
            ),
            starterFiles = mapOf(
                "App.jsx" to """import React, { useState } from 'react';

export default function Calculator() {
  const [display, setDisplay] = useState('0');
  return <div className="calc"><h3>{display}</h3></div>;
}"""
            ),
            tags = listOf("useState", "Components")
        ),
        ReactProject(
            id = "proj_weather",
            title = "SkyPulse Live Weather App",
            level = ProjectLevel.BEGINNER,
            category = "API & Data",
            estimatedHours = 4,
            summary = "Fetch real-time weather, 5-day forecasts, temperature toggle (C/F), and dynamic background animations reflecting current atmospheric conditions.",
            keyFeatures = listOf("City search with debounce", "Current temp, humidity, wind", "5-day forecast cards", "Offline cached last search"),
            architecturalConcepts = listOf("Async data fetching", "Loading skeletons", "Error boundaries", "Conditional styling"),
            milestones = listOf(
                Milestone("w1", "API Integration", "Fetch OpenWeather JSON", true),
                Milestone("w2", "Forecast Cards", "Map 5-day forecast list", false),
                Milestone("w3", "Unit Conversion", "Toggle Celsius and Fahrenheit", false)
            ),
            starterFiles = mapOf(
                "App.jsx" to """import React, { useState, useEffect } from 'react';

export default function WeatherApp() {
  const [city, setCity] = useState('Kathmandu');
  const [weather, setWeather] = useState({ temp: 24, condition: 'Sunny', humidity: 55 });

  return (
    <div className="weather-card">
      <h2>{city}</h2>
      <h1>{weather.temp}°C</h1>
      <p>{weather.condition} • Humidity: {weather.humidity}%</p>
    </div>
  );
}"""
            ),
            tags = listOf("APIs", "useEffect", "Conditional UI")
        ),
        ReactProject(
            id = "proj_notes",
            title = "Markdown Scratchpad Notes",
            level = ProjectLevel.BEGINNER,
            category = "Productivity",
            estimatedHours = 4,
            summary = "Fast note-taking app with live Markdown preview, tag categorization, search filter, and instant local storage backup.",
            keyFeatures = listOf("Live side-by-side Markdown editor", "Note pin & archive", "Color tags", "Word & character counter"),
            architecturalConcepts = listOf("Controlled textarea", "Derived search results", "Custom hooks"),
            milestones = listOf(
                Milestone("n1", "Note CRUD", "Create, read, update, delete notes", true),
                Milestone("n2", "Search & Tags", "Filter notes by keyword and tags", false)
            ),
            starterFiles = mapOf("App.jsx" to "export default function Notes() { return <h2>Notes App</h2>; }"),
            tags = listOf("State", "Custom Hooks")
        ),
        ReactProject(
            id = "proj_quiz",
            title = "Interactive React Quiz Master",
            level = ProjectLevel.BEGINNER,
            category = "Education",
            estimatedHours = 3,
            summary = "A gamified quiz application with timer countdown, score tracking, progress bar, and comprehensive results breakdown.",
            keyFeatures = listOf("10 React questions with timer", "Instant answer feedback", "Score calculation and badges", "Review incorrect questions"),
            architecturalConcepts = listOf("Step-based state machine", "Timer with cleanup", "Array shuffling"),
            milestones = listOf(
                Milestone("q1", "Question Flow", "Advance through question index", true),
                Milestone("q2", "Timer Countdown", "Tick every second with useEffect", false)
            ),
            starterFiles = mapOf("App.jsx" to "export default function Quiz() { return <h2>React Quiz</h2>; }"),
            tags = listOf("State", "useReducer")
        ),
        ReactProject(
            id = "proj_clock",
            title = "World Clock & Timezone Hub",
            level = ProjectLevel.BEGINNER,
            category = "Utilities",
            estimatedHours = 2,
            summary = "Displays live digital and analog clocks across international cities with sunrise/sunset indicators and timezone converter.",
            keyFeatures = listOf("Multiple city clocks", "12/24 hour format toggle", "Analog canvas watch face", "Timezone difference calculator"),
            architecturalConcepts = listOf("Date object formatting", "1-second interval effects", "Canvas rendering in React"),
            milestones = listOf(Milestone("ck1", "Clock Tick", "1-second state tick", true)),
            starterFiles = mapOf("App.jsx" to "export default function Clock() { return <h2>World Clock</h2>; }"),
            tags = listOf("useEffect", "Canvas")
        ),
        ReactProject(
            id = "proj_expense",
            title = "SmartBudget Expense Tracker",
            level = ProjectLevel.BEGINNER,
            category = "Finance",
            estimatedHours = 4,
            summary = "Track income and expenses, calculate balance, categorize spending, and visualize spending breakdowns with bar charts.",
            keyFeatures = listOf("Add income/expense transactions", "Running net balance", "Category badges (Food, Rent, Salary)", "Delete transaction"),
            architecturalConcepts = listOf("useReducer for financial entries", "Array reduce calculations", "Chart composition"),
            milestones = listOf(Milestone("e1", "Transaction State", "Add & delete transactions", true)),
            starterFiles = mapOf("App.jsx" to "export default function Expense() { return <h2>Budget Tracker</h2>; }"),
            tags = listOf("useReducer", "State")
        ),

        // INTERMEDIATE PROJECTS
        ReactProject(
            id = "proj_movie",
            title = "CineScope Movie Explorer",
            level = ProjectLevel.INTERMEDIATE,
            category = "Entertainment",
            estimatedHours = 6,
            summary = "Search films via TMDB API, view trailers, sort by rating and release date, filter by genres, and maintain a Watchlist in Context.",
            keyFeatures = listOf("Live search with 300ms debounce", "Genre chip filters", "Watchlist global context", "Movie details modal with trailer"),
            architecturalConcepts = listOf("useContext for Watchlist", "Custom useDebounce hook", "REST API integration", "Modal portal rendering"),
            milestones = listOf(
                Milestone("m1", "Search & Grid", "Fetch movie list and render cards", true),
                Milestone("m2", "Watchlist Context", "Add to watchlist provider", false),
                Milestone("m3", "Debounced Search", "Prevent rapid API spam", false)
            ),
            starterFiles = mapOf("App.jsx" to "export default function MovieExplorer() { return <h2>CineScope</h2>; }"),
            tags = listOf("useContext", "Custom Hooks", "APIs")
        ),
        ReactProject(
            id = "proj_ecommerce_ui",
            title = "Aura Store Modern E-Commerce UI",
            level = ProjectLevel.INTERMEDIATE,
            category = "Commerce",
            estimatedHours = 8,
            summary = "Full online store frontend featuring product catalogs, category tabs, drawer shopping cart, quantity adjustments, and checkout flow.",
            keyFeatures = listOf("Product grid with discount badges", "Slide-over cart drawer", "Price calculation with tax & shipping", "Product image gallery selector"),
            architecturalConcepts = listOf("useReducer for cart items", "Compound components for modal/drawer", "Optimistic cart updates"),
            milestones = listOf(
                Milestone("ec1", "Catalog Grid", "Display products with filter chips", true),
                Milestone("ec2", "Cart Drawer", "useReducer cart with increment/decrement", false),
                Milestone("ec3", "Checkout Summary", "Calculate subtotals, tax, discounts", false)
            ),
            starterFiles = mapOf("App.jsx" to "export default function Store() { return <h2>Aura Store</h2>; }"),
            tags = listOf("useReducer", "Component Design", "UI")
        ),
        ReactProject(
            id = "proj_github",
            title = "DevRadar GitHub Profile Explorer",
            level = ProjectLevel.INTERMEDIATE,
            category = "Developer Tools",
            estimatedHours = 5,
            summary = "Inspect developer profiles on GitHub: repositories, star counts, top languages chart, follower networks, and commit activity history.",
            keyFeatures = listOf("GitHub REST API integration", "Pinned repositories list", "Language breakdown visualization", "User search history"),
            architecturalConcepts = listOf("Async error handling", "Language percentage calculations", "Responsive profile layout"),
            milestones = listOf(
                Milestone("gh1", "Profile Card", "Fetch user profile details", true),
                Milestone("gh2", "Repo List", "Fetch and sort repositories", false)
            ),
            starterFiles = mapOf("App.jsx" to "export default function DevRadar() { return <h2>DevRadar</h2>; }"),
            tags = listOf("APIs", "Async/Await")
        ),
        ReactProject(
            id = "proj_chat_ui",
            title = "EchoPulse Realtime Chat UI",
            level = ProjectLevel.INTERMEDIATE,
            category = "Communication",
            estimatedHours = 6,
            summary = "A responsive instant messaging interface with active channels, typing indicators, simulated message responses, and emoji reactions.",
            keyFeatures = listOf("Channel list & direct messages", "Message bubbles with timestamp", "Auto-scroll to bottom with useRef", "Simulated bot replies"),
            architecturalConcepts = listOf("useRef for DOM auto-scroll", "Message array updates", "Typing simulation timer"),
            milestones = listOf(
                Milestone("ch1", "Message Layout", "Sender vs Receiver bubbles", true),
                Milestone("ch2", "Auto-Scroll", "useRef scrollIntoView", false)
            ),
            starterFiles = mapOf("App.jsx" to "export default function ChatApp() { return <h2>EchoPulse</h2>; }"),
            tags = listOf("useRef", "State", "useEffect")
        ),
        ReactProject(
            id = "proj_admin",
            title = "Apex Metric Admin Dashboard",
            level = ProjectLevel.INTERMEDIATE,
            category = "Enterprise",
            estimatedHours = 7,
            summary = "An executive SaaS dashboard with KPIs, revenue graphs, user management data table with sorting and pagination, and dark/light mode.",
            keyFeatures = listOf("KPI statistics cards with percent change", "Data table with multi-column sorting", "Pagination controls", "Sidebar navigation with active state"),
            architecturalConcepts = listOf("Sorting algorithms in derived state", "Table pagination calculations", "Theme context"),
            milestones = listOf(Milestone("ad1", "KPI Grid", "Display metrics", true)),
            starterFiles = mapOf("App.jsx" to "export default function Admin() { return <h2>Apex Dashboard</h2>; }"),
            tags = listOf("Data Tables", "Theme Context")
        ),
        ReactProject(
            id = "proj_recipe",
            title = "FlavorCraft Recipe & Meal Planner",
            level = ProjectLevel.INTERMEDIATE,
            category = "Lifestyle",
            estimatedHours = 5,
            summary = "Discover global recipes, calculate servings adjustments, generate grocery checklists, and bookmark favorite dietary dishes.",
            keyFeatures = listOf("Search by ingredients", "Dynamic servings multiplier", "Ingredient checklist", "Nutrition facts breakdown"),
            architecturalConcepts = listOf("Derived serving ratios", "Checkbox array state", "Local storage bookmarks"),
            milestones = listOf(Milestone("rc1", "Recipe Search", "Fetch recipes", true)),
            starterFiles = mapOf("App.jsx" to "export default function Recipes() { return <h2>FlavorCraft</h2>; }"),
            tags = listOf("State", "APIs")
        ),
        ReactProject(
            id = "proj_taskmgr",
            title = "KanbanBoard Agile Task Manager",
            level = ProjectLevel.INTERMEDIATE,
            category = "Productivity",
            estimatedHours = 7,
            summary = "Trello-style drag and drop task board with columns for Backlog, In Progress, Review, and Done, with priority badges and assignees.",
            keyFeatures = listOf("Column task grouping", "Move task between columns", "Add new column", "Task labels and due dates"),
            architecturalConcepts = listOf("Normalized state structures", "State lifting", "Drag event handling"),
            milestones = listOf(Milestone("kb1", "Column Rendering", "Render 4 columns", true)),
            starterFiles = mapOf("App.jsx" to "export default function Kanban() { return <h2>Kanban Board</h2>; }"),
            tags = listOf("Normalized State", "Architecture")
        ),
        ReactProject(
            id = "proj_blog",
            title = "DevJournal Tech Blog Platform",
            level = ProjectLevel.INTERMEDIATE,
            category = "Publishing",
            estimatedHours = 6,
            summary = "A modern developer blog with category filtering, reading time estimates, author bio cards, social share links, and comment section.",
            keyFeatures = listOf("Article feed with thumbnails", "Full article reader view", "Reading time calculation", "Interactive comment thread"),
            architecturalConcepts = listOf("SPA routing concepts", "Recursive comment components", "Text parsing"),
            milestones = listOf(Milestone("bl1", "Article List", "Render articles", true)),
            starterFiles = mapOf("App.jsx" to "export default function Blog() { return <h2>DevJournal</h2>; }"),
            tags = listOf("Routing", "Components")
        ),

        // ADVANCED PROJECTS
        ReactProject(
            id = "proj_lms",
            title = "CodeAcademy Full LMS Platform",
            level = ProjectLevel.ADVANCED,
            category = "Education",
            estimatedHours = 12,
            summary = "A comprehensive learning management system with course catalog, video lesson player, code exercises, quiz evaluations, and certificate generation.",
            keyFeatures = listOf("Course curriculum tree with completion checks", "Interactive code runner embedded in lessons", "Progress tracking with streak", "Dynamic PDF-style certificate generator"),
            architecturalConcepts = listOf("Global course state machine", "Compound lesson player", "Performance memoization"),
            milestones = listOf(
                Milestone("lms1", "Curriculum Engine", "Structured module hierarchy", true),
                Milestone("lms2", "Interactive Workspace", "Code editor + preview split", false),
                Milestone("lms3", "Certification", "Compute completion and award certificate", false)
            ),
            starterFiles = mapOf("App.jsx" to "export default function LMS() { return <h2>CodeAcademy LMS</h2>; }"),
            tags = listOf("Full Architecture", "Zustand/Redux", "Testing")
        ),
        ReactProject(
            id = "proj_analytics",
            title = "Vortex Real-Time Analytics Platform",
            level = ProjectLevel.ADVANCED,
            category = "Enterprise",
            estimatedHours = 10,
            summary = "High-performance streaming telemetry dashboard with SVG charts, real-time simulated socket data, latency monitors, and log filters.",
            keyFeatures = listOf("60fps data stream visualization", "React.memo & useCallback optimizations", "Filterable event stream table", "Alert thresholds trigger"),
            architecturalConcepts = listOf("Performance profiling", "React.memo against render thrashing", "useRef for socket streams"),
            milestones = listOf(Milestone("an1", "Stream Setup", "Simulated event stream", true)),
            starterFiles = mapOf("App.jsx" to "export default function Vortex() { return <h2>Vortex Analytics</h2>; }"),
            tags = listOf("Performance", "React.memo", "useCallback")
        ),
        ReactProject(
            id = "proj_social",
            title = "ConnectSphere Social Network Hub",
            level = ProjectLevel.ADVANCED,
            category = "Social",
            estimatedHours = 12,
            summary = "Social platform with post feeds, image uploads, nested comments, like reactions, notification center, and user profile management.",
            keyFeatures = listOf("Infinite scroll post feed", "Optimistic like and bookmark updates", "Nested reply comment trees", "Modal image lightbox"),
            architecturalConcepts = listOf("Optimistic UI mutations", "Infinite pagination hooks", "Context architecture"),
            milestones = listOf(Milestone("so1", "Feed Architecture", "Infinite scroll list", true)),
            starterFiles = mapOf("App.jsx" to "export default function Social() { return <h2>ConnectSphere</h2>; }"),
            tags = listOf("Optimistic UI", "Infinite Scroll")
        ),
        ReactProject(
            id = "proj_finance",
            title = "ApexTrade Crypto & Stock Terminal",
            level = ProjectLevel.ADVANCED,
            category = "Finance",
            estimatedHours = 10,
            summary = "Professional financial trading terminal with candlestick chart previews, order book simulation, portfolio balancing, and currency conversion.",
            keyFeatures = listOf("Live price tickers with flashing green/red updates", "Order book bid/ask depth view", "Portfolio pie chart allocation", "Buy/Sell limit order form"),
            architecturalConcepts = listOf("High frequency render containment", "Custom mathematical hooks", "Strict TypeScript interfaces"),
            milestones = listOf(Milestone("fn1", "Order Book", "Render depth chart", true)),
            starterFiles = mapOf("App.jsx" to "export default function Terminal() { return <h2>ApexTrade</h2>; }"),
            tags = listOf("TypeScript", "Performance", "Finance")
        )
    )

    fun getProject(id: String): ReactProject? = allProjects.find { it.id == id }
}
