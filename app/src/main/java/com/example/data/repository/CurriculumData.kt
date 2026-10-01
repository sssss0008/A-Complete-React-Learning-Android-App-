package com.example.data.repository

import com.example.data.model.CurriculumLevel
import com.example.data.model.Lesson
import com.example.data.model.VisualizerType

object CurriculumData {

    val allLevels: List<CurriculumLevel> = listOf(
        CurriculumLevel(
            level = 1,
            title = "Web Development Foundation",
            category = "Foundation",
            description = "Master how browsers render web pages, the DOM tree, HTTP requests, and the modern JavaScript runtime before jumping into React.",
            iconName = "globe",
            lessons = listOf(
                Lesson(
                    id = "lvl1_l1",
                    levelNumber = 1,
                    title = "How Browsers & the DOM Work",
                    subtitle = "HTML parsing, CSSOM construction, and DOM tree representation",
                    durationMinutes = 12,
                    difficulty = "Beginner",
                    keyTakeaways = listOf(
                        "Browsers parse HTML into a Document Object Model (DOM) tree",
                        "Direct DOM updates trigger expensive layout recalculation and repaints",
                        "React abstracts DOM operations using declarative JSX and a Virtual DOM"
                    ),
                    explanationMarkdown = "The browser converts raw HTML bytes into tokens, then into DOM nodes. When JavaScript changes the DOM directly via `document.getElementById()`, the browser triggers reflow and repaint. React was invented to eliminate repetitive manual DOM mutations.",
                    visualizerType = VisualizerType.COMPONENT_TREE,
                    initialCode = """// Traditional Imperative DOM manipulation
const button = document.createElement('button');
button.innerText = 'Clicks: 0';
let count = 0;
button.addEventListener('click', () => {
  count++;
  button.innerText = 'Clicks: ' + count; // Direct DOM manipulation
});
document.body.appendChild(button);""",
                    interactiveDemoType = "dom_demo",
                    quizQuestion = "Why is direct, repeated DOM manipulation considered expensive in browser rendering?",
                    quizOptions = listOf(
                        "It forces browser reflow, layout recalculation, and repainting",
                        "Browsers do not support JavaScript accessing DOM nodes",
                        "The DOM cannot store text values",
                        "HTML files cannot be modified after download"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Every time an element's dimensions or hierarchy changes, the browser must recalculate geometric positions (layout/reflow) and re-render pixels (repaint)."
                ),
                Lesson(
                    id = "lvl1_l2",
                    levelNumber = 1,
                    title = "HTTP, JSON & Modern Tooling",
                    subtitle = "npm, package.json, bundlers (Vite/Webpack), and ES Modules",
                    durationMinutes = 10,
                    difficulty = "Beginner",
                    keyTakeaways = listOf(
                        "npm manages dependencies declared in package.json",
                        "ES Modules (import/export) allow clean modular separation of code",
                        "Vite bundles modern React applications using fast native ESM"
                    ),
                    explanationMarkdown = "Modern frontend applications rely on package managers like npm or yarn and build tools like Vite. React is delivered as npm packages (`react` and `react-dom`).",
                    visualizerType = VisualizerType.NONE,
                    initialCode = """// ES Module syntax
import React from 'react';
import { useState } from 'react';

export function calculateTotal(items) {
  return items.reduce((acc, item) => acc + item.price, 0);
}""",
                    interactiveDemoType = "esmodule_demo",
                    quizQuestion = "Which statement best describes an ES Module in modern JavaScript?",
                    quizOptions = listOf(
                        "Files that use 'import' and 'export' to share scoped functions and values",
                        "A special browser plugin required to run React",
                        "A backend database table for storing JSON",
                        "An HTML tag used strictly for styling"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "ES Modules are the official standard format for packaging JavaScript code for reuse, using the import and export statements."
                )
            )
        ),
        CurriculumLevel(
            level = 2,
            title = "JavaScript for React",
            category = "Foundation",
            description = "Destructuring, spread operator, map/filter/reduce, promises, async/await, optional chaining, and arrow functions.",
            iconName = "code",
            lessons = listOf(
                Lesson(
                    id = "lvl2_l1",
                    levelNumber = 2,
                    title = "Essential ES6+ Syntax for React",
                    subtitle = "Destructuring, spread/rest, arrow functions, and template literals",
                    durationMinutes = 15,
                    difficulty = "Beginner",
                    keyTakeaways = listOf(
                        "Destructuring allows extracting object properties and array items cleanly",
                        "The spread operator `...` allows shallow copying objects and arrays immutably",
                        "Arrow functions provide concise syntax and lexical `this` binding"
                    ),
                    explanationMarkdown = "React relies heavily on modern JavaScript. In React, we never mutate state directly; instead, we use the spread operator (`[...items, newItem]`) to create new copies with updates.",
                    visualizerType = VisualizerType.PROPS_FLOW,
                    initialCode = """// Object & Array Destructuring
const user = { name: 'Awiskar', role: 'React Developer', city: 'Kathmandu' };
const { name, role } = user;

// Immutable Array Update with Spread
const tasks = ['Learn JS', 'Learn React'];
const updatedTasks = [...tasks, 'Build Apps'];

console.log(name + ' is a ' + role);
console.log('Tasks:', updatedTasks);""",
                    interactiveDemoType = "spread_demo",
                    quizQuestion = "How do you add an item to an array immutably in React without mutating the original?",
                    quizOptions = listOf(
                        "const next = [...prev, newItem];",
                        "prev.push(newItem);",
                        "prev[prev.length] = newItem;",
                        "prev.append(newItem);"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Using spread `[...prev, newItem]` creates a brand new array reference with all previous items plus the new item, allowing React to detect state changes."
                ),
                Lesson(
                    id = "lvl2_l2",
                    levelNumber = 2,
                    title = "Array Methods: map, filter, reduce",
                    subtitle = "Transforming lists into UI elements declaratively",
                    durationMinutes = 14,
                    difficulty = "Beginner",
                    keyTakeaways = listOf(
                        "Array.prototype.map() transforms data items into React JSX elements",
                        "filter() creates a subset of items matching a condition",
                        "reduce() computes a single cumulative value like totals or counts"
                    ),
                    explanationMarkdown = "In React, we do not use `for` loops inside JSX. Instead, we use `.map()` to iterate over arrays and return JSX elements, and `.filter()` to display conditional subsets.",
                    visualizerType = VisualizerType.NONE,
                    initialCode = """const techStack = ['React', 'TypeScript', 'Tailwind', 'Next.js'];

// Transforming strings into JSX list items
const items = techStack.map((tech, index) => (
  <li key={index}>{tech}</li>
));""",
                    interactiveDemoType = "map_demo",
                    quizQuestion = "Why is Array.prototype.map() the standard way to render lists in React JSX?",
                    quizOptions = listOf(
                        "It returns a new array of JSX elements that React can render",
                        "It modifies the original array in place",
                        "It prevents re-renders automatically",
                        "It is required by the JavaScript compiler"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "map() produces a new array of elements from an existing array without mutating the source, perfectly fitting React's declarative model."
                )
            )
        ),
        CurriculumLevel(
            level = 3,
            title = "React Fundamentals",
            category = "React Core",
            description = "What React is, why it was created, declarative vs imperative UI, and the Virtual DOM reconciliation engine.",
            iconName = "atom",
            lessons = listOf(
                Lesson(
                    id = "lvl3_l1",
                    levelNumber = 3,
                    title = "Declarative UI & The Virtual DOM",
                    subtitle = "How React compares snapshots and batches real DOM updates",
                    durationMinutes = 15,
                    difficulty = "Beginner",
                    keyTakeaways = listOf(
                        "Declarative UI describes WHAT the UI should look like for a given state",
                        "Virtual DOM is an in-memory lightweight representation of the real DOM",
                        "Reconciliation computes the minimal diff to patch the actual browser DOM"
                    ),
                    explanationMarkdown = "In imperative programming (jQuery/vanilla JS), you write step-by-step instructions to change colors, insert elements, and remove children. In declarative React, you write: `UI = f(state)`. When state changes, React figures out the optimal changes.",
                    visualizerType = VisualizerType.VIRTUAL_DOM_DIFF,
                    initialCode = """import React, { useState } from 'react';

export default function CounterApp() {
  const [count, setCount] = useState(0);

  // Declarative UI: Describe the UI based on current count
  return (
    <div className="card">
      <h2>Count: {count}</h2>
      <button onClick={() => setCount(count + 1)}>Increment</button>
    </div>
  );
}""",
                    interactiveDemoType = "counter",
                    quizQuestion = "What is the primary role of the Virtual DOM in React?",
                    quizOptions = listOf(
                        "To calculate the minimal diff and batch updates to the real browser DOM",
                        "To replace the browser's JavaScript engine completely",
                        "To store user passwords securely in the cloud",
                        "To compile CSS styles into bytecode"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "The Virtual DOM creates a lightweight tree representation in memory so React can diff changes (Reconciliation) and only touch the real DOM where necessary."
                )
            )
        ),
        CurriculumLevel(
            level = 4,
            title = "JSX Syntax & Deep Dive",
            category = "React Core",
            description = "JSX expressions, fragments, conditional rendering, attributes, inline styles, and JSX compiling to React.createElement.",
            iconName = "layers",
            lessons = listOf(
                Lesson(
                    id = "lvl4_l1",
                    levelNumber = 4,
                    title = "Understanding JSX Under the Hood",
                    subtitle = "JavaScript XML, curly braces expressions, and React Fragments",
                    durationMinutes = 14,
                    difficulty = "Beginner",
                    keyTakeaways = listOf(
                        "JSX is syntactic sugar for React.createElement() calls",
                        "Curly braces `{}` evaluate any valid JavaScript expression inside JSX",
                        "React.Fragment `<>` allows grouping multiple elements without extra DOM wrapper divs"
                    ),
                    explanationMarkdown = "Browsers cannot read JSX natively. Babel/Vite compiles JSX into JavaScript functions: `<h1 className='title'>Hello</h1>` becomes `React.createElement('h1', { className: 'title' }, 'Hello')`.",
                    visualizerType = VisualizerType.COMPONENT_TREE,
                    initialCode = """import React from 'react';

export default function UserGreeting() {
  const user = { name: 'Awiskar', isOnline: true };

  return (
    <>
      <h1>Welcome, {user.name}!</h1>
      <span className={user.isOnline ? 'badge-green' : 'badge-gray'}>
        Status: {user.isOnline ? 'Online' : 'Offline'}
      </span>
    </>
  );
}""",
                    interactiveDemoType = "jsx_demo",
                    quizQuestion = "What does JSX compile to before running in the browser?",
                    quizOptions = listOf(
                        "React.createElement() JavaScript function calls",
                        "Raw binary machine code",
                        "Static HTML strings injected with innerHTML",
                        "WebAssembly modules"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "JSX tags are transformed by compilers into React.createElement() function calls that produce lightweight JavaScript object descriptors."
                )
            )
        ),
        CurriculumLevel(
            level = 5,
            title = "Components & Hierarchy",
            category = "React Core",
            description = "Functional components, component composition, single responsibility principle, and component tree architecture.",
            iconName = "grid",
            lessons = listOf(
                Lesson(
                    id = "lvl5_l1",
                    levelNumber = 5,
                    title = "Building Reusable Functional Components",
                    subtitle = "Component trees: App → Header → MainContent → Cards → Footer",
                    durationMinutes = 16,
                    difficulty = "Beginner",
                    keyTakeaways = listOf(
                        "Components must start with a Capital letter (e.g. UserCard, not userCard)",
                        "Components are pure functions that accept props and return JSX",
                        "Complex UIs are composed of small, focused, reusable sub-components"
                    ),
                    explanationMarkdown = "Components let you split the UI into independent, reusable pieces. Think of them like Lego bricks: a Header component, a Card component, an Avatar component, all assembled into an App.",
                    visualizerType = VisualizerType.COMPONENT_TREE,
                    initialCode = """function Header({ title }) {
  return <header><h1>{title}</h1></header>;
}

function MetricCard({ label, value }) {
  return (
    <div className="metric">
      <span>{label}</span>
      <strong>{value}</strong>
    </div>
  );
}

export default function Dashboard() {
  return (
    <div>
      <Header title="React Academy" />
      <MetricCard label="Active Students" value="1,240" />
      <MetricCard label="Completion Rate" value="94%" />
    </div>
  );
}""",
                    interactiveDemoType = "component_tree_demo",
                    quizQuestion = "Why must React component function names start with a capital letter?",
                    quizOptions = listOf(
                        "React treats lowercase tags as native HTML elements (div, span) and uppercase tags as custom components",
                        "JavaScript throws a syntax error on lowercase functions",
                        "Capital letters make the code run faster in production",
                        "It is required by the CSS styling specification"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "JSX uses capitalization to distinguish between built-in HTML tags (like <div> or <p>) and custom React components (like <Header />)."
                )
            )
        ),
        CurriculumLevel(
            level = 6,
            title = "Props & Unidirectional Data Flow",
            category = "React Core",
            description = "Passing data from parent to child, default props, children prop, destructuring props, and immutable props rules.",
            iconName = "share-2",
            lessons = listOf(
                Lesson(
                    id = "lvl6_l1",
                    levelNumber = 6,
                    title = "Props: Passing Data Downward",
                    subtitle = "Parent Component → Props → Child Component",
                    durationMinutes = 15,
                    difficulty = "Beginner",
                    keyTakeaways = listOf(
                        "Props (properties) are read-only inputs passed from parent to child",
                        "A component must NEVER modify its own props (pure functions)",
                        "The special `children` prop allows wrapping arbitrary content inside a container"
                    ),
                    explanationMarkdown = "Data flows down in React, like a waterfall. A parent passes values to child components through props. If the child needs to trigger a change in the parent, the parent passes down a callback function as a prop.",
                    visualizerType = VisualizerType.PROPS_FLOW,
                    initialCode = """function StatusBadge({ status = 'active', role }) {
  const color = status === 'active' ? '#10B981' : '#F59E0B';
  return (
    <div style={{ borderColor: color }} className="badge">
      <span style={{ backgroundColor: color }} className="dot" />
      <span>{role} ({status})</span>
    </div>
  );
}

export default function TeamList() {
  return (
    <div>
      <StatusBadge role="Frontend Lead" status="active" />
      <StatusBadge role="UI Designer" status="away" />
    </div>
  );
}""",
                    interactiveDemoType = "props_demo",
                    quizQuestion = "Can a child component directly reassign or mutate the props it receives?",
                    quizOptions = listOf(
                        "No, props are strictly read-only and immutable",
                        "Yes, by using props.property = newValue",
                        "Yes, but only if the prop is a number",
                        "Only inside an asynchronous function"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "React components must act like pure functions with respect to their props: never mutate input props directly."
                )
            )
        ),
        CurriculumLevel(
            level = 7,
            title = "State & useState()",
            category = "React Core",
            description = "Component memory with useState, state updates, functional updater syntax, derived state, and re-rendering cycle.",
            iconName = "database",
            lessons = listOf(
                Lesson(
                    id = "lvl7_l1",
                    levelNumber = 7,
                    title = "State: The Component's Memory",
                    subtitle = "User Action → State Update → Re-render → Updated UI",
                    durationMinutes = 18,
                    difficulty = "Beginner",
                    keyTakeaways = listOf(
                        "`useState` returns an array with the current value and a setter function: `[value, setValue]`",
                        "Calling `setValue()` schedules a re-render with the new state snapshot",
                        "Use functional updates `setCount(prev => prev + 1)` when next state depends on current state"
                    ),
                    explanationMarkdown = "Props are external inputs passed into a component. State is internal memory managed inside the component. When state updates, React re-executes the component function to render the updated UI.",
                    visualizerType = VisualizerType.STATE_CYCLE,
                    initialCode = """import React, { useState } from 'react';

export default function Counter() {
  const [count, setCount] = useState(0);

  const increment = () => {
    // Functional update ensures correct state in async batches
    setCount(prevCount => prevCount + 1);
  };

  const reset = () => setCount(0);

  return (
    <div className="counter-box">
      <h3>Current Count: {count}</h3>
      <button onClick={increment}>+ Add 1</button>
      <button onClick={reset}>Reset</button>
    </div>
  );
}""",
                    interactiveDemoType = "counter",
                    quizQuestion = "When should you use the functional updater form `setCount(prev => prev + 1)`?",
                    quizOptions = listOf(
                        "Whenever the new state depends on the previous state value",
                        "Only when using numbers",
                        "Only inside useEffect hooks",
                        "Whenever fetching data from an external API"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Because state updates may be batched asynchronously, the functional updater guarantees you are computing the new state using the freshest previous state."
                )
            )
        ),
        CurriculumLevel(
            level = 8,
            title = "Event Handling",
            category = "React Core",
            description = "SyntheticEvent system, click, input, form submission, keyboard events, passing parameters, and e.preventDefault().",
            iconName = "zap",
            lessons = listOf(
                Lesson(
                    id = "lvl8_l1",
                    levelNumber = 8,
                    title = "Handling User Events in React",
                    subtitle = "SyntheticEvents, event listeners, and preventDefault",
                    durationMinutes = 14,
                    difficulty = "Beginner",
                    keyTakeaways = listOf(
                        "React event handlers use camelCase: `onClick`, `onChange`, `onSubmit`",
                        "Pass functions as event handlers, do not invoke them immediately (e.g. `onClick={handleClick}`, NOT `onClick={handleClick()}`)",
                        "`e.preventDefault()` prevents standard browser actions like form page refreshes"
                    ),
                    explanationMarkdown = "React wraps the browser's native event with a `SyntheticEvent` object to ensure consistent behavior across all browsers.",
                    visualizerType = VisualizerType.NONE,
                    initialCode = """import React, { useState } from 'react';

export default function FormExample() {
  const [text, setText] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault(); // Stop full-page reload
    alert('Submitted: ' + text);
  };

  return (
    <form onSubmit={handleSubmit}>
      <input 
        type="text" 
        value={text} 
        onChange={(e) => setText(e.target.value)} 
        placeholder="Type here..." 
      />
      <button type="submit">Submit</button>
    </form>
  );
}""",
                    interactiveDemoType = "form",
                    quizQuestion = "What happens if you write `<button onClick={handleClick()}>Click</button>` instead of `<button onClick={handleClick}>Click</button>`?",
                    quizOptions = listOf(
                        "The function executes immediately during render instead of waiting for the click",
                        "React throws a compilation syntax error",
                        "The button becomes permanently disabled",
                        "The browser crashes due to memory overflow"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Adding parentheses `()` invokes the function during render. You should pass the function reference `handleClick` so React calls it when the click occurs."
                )
            )
        ),
        CurriculumLevel(
            level = 9,
            title = "Conditional Rendering",
            category = "React Core",
            description = "Ternary operators, logical AND (&&), early return guard clauses, loading states, and error fallbacks.",
            iconName = "toggle-right",
            lessons = listOf(
                Lesson(
                    id = "lvl9_l1",
                    levelNumber = 9,
                    title = "Rendering UI Conditionally",
                    subtitle = "Ternary `? :`, Logical `&&`, and Guard Clauses",
                    durationMinutes = 14,
                    difficulty = "Beginner",
                    keyTakeaways = listOf(
                        "Ternary operator `condition ? <A /> : <B />` handles two alternative branches",
                        "Logical AND `condition && <Component />` renders element only if condition is true",
                        "Be careful with `0 && <Component />` which renders `0` in browser text"
                    ),
                    explanationMarkdown = "In React, you can conditionally render elements using standard JavaScript syntax. Guard clauses at the top of a component are great for handling loading and error states cleanly.",
                    visualizerType = VisualizerType.NONE,
                    initialCode = """export default function ProfileView({ user, isLoading, error }) {
  if (isLoading) return <div>Loading user profile...</div>;
  if (error) return <div className="error">Failed to load: {error}</div>;
  if (!user) return <div>No user found.</div>;

  return (
    <div>
      <h2>{user.name}</h2>
      {user.isPremium && <span className="vip">⭐ Premium Member</span>}
    </div>
  );
}""",
                    interactiveDemoType = "conditional_demo",
                    quizQuestion = "Why might `items.length && <List items={items} />` display the number '0' on screen when items is empty?",
                    quizOptions = listOf(
                        "In JavaScript, 0 is falsy, so the && expression evaluates to 0, which React renders as text",
                        "React always requires ternary operators",
                        "Arrays cannot be checked for length inside JSX",
                        "The DOM cannot render empty lists"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "To avoid displaying '0', write `items.length > 0 && <List />` or a ternary `items.length ? <List /> : null`."
                )
            )
        ),
        CurriculumLevel(
            level = 10,
            title = "Lists, Keys & Todo Laboratory",
            category = "React Core",
            description = "Array mapping, why keys are essential, stable vs index keys, and building an interactive Todo list.",
            iconName = "list",
            lessons = listOf(
                Lesson(
                    id = "lvl10_l1",
                    levelNumber = 10,
                    title = "Lists and the Key Prop",
                    subtitle = "How keys help React match Virtual DOM nodes across renders",
                    durationMinutes = 16,
                    difficulty = "Beginner",
                    keyTakeaways = listOf(
                        "Every item in a rendered list must have a unique `key` prop among siblings",
                        "Keys help React identify which items have changed, been added, or been removed",
                        "Avoid using array indexes as keys when list items can be reordered, inserted, or deleted"
                    ),
                    explanationMarkdown = "Without stable keys, reordering or filtering a list forces React to re-render all children and can cause input state bugs. Always use unique IDs from your data.",
                    visualizerType = VisualizerType.VIRTUAL_DOM_DIFF,
                    initialCode = """import React, { useState } from 'react';

export default function TodoLab() {
  const [todos, setTodos] = useState([
    { id: '1', text: 'Learn React Core', done: true },
    { id: '2', text: 'Master Hooks', done: false },
    { id: '3', text: 'Build Projects', done: false }
  ]);

  const toggle = (id) => {
    setTodos(todos.map(t => t.id === id ? { ...t, done: !t.done } : t));
  };

  return (
    <ul>
      {todos.map(todo => (
        <li key={todo.id} onClick={() => toggle(todo.id)}>
          {todo.done ? '✅ ' : '⬜ '} {todo.text}
        </li>
      ))}
    </ul>
  );
}""",
                    interactiveDemoType = "todo",
                    quizQuestion = "Why is using array index as a `key` considered an anti-pattern when list order can change?",
                    quizOptions = listOf(
                        "It causes subtle bugs with input state and harms reconciliation performance when items are sorted or deleted",
                        "React throws a fatal runtime exception if index is used",
                        "Indexes cannot be converted to strings",
                        "The browser prevents rendering arrays with numeric keys"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "When the array order changes or items are removed, indexes change, misleading React into preserving state in the wrong component instances."
                )
            )
        ),
        CurriculumLevel(
            level = 11,
            title = "Forms & Controlled Components",
            category = "React Core",
            description = "Controlled vs uncontrolled components, handling text inputs, checkboxes, selects, and multi-step forms.",
            iconName = "edit-3",
            lessons = listOf(
                Lesson(
                    id = "lvl11_l1",
                    levelNumber = 11,
                    title = "Controlled Inputs & Form State",
                    subtitle = "Single source of truth via React state and onChange handlers",
                    durationMinutes = 16,
                    difficulty = "Intermediate",
                    keyTakeaways = listOf(
                        "In a controlled component, the input value is driven by React state: `value={state}`",
                        "State updates via `onChange={(e) => setState(e.target.value)}`",
                        "Controlled inputs enable live validation, formatting, and submit disabling"
                    ),
                    explanationMarkdown = "In HTML, form elements manage their own state. In React, making them controlled makes React state the 'single source of truth'.",
                    visualizerType = VisualizerType.STATE_CYCLE,
                    initialCode = """import React, { useState } from 'react';

export default function RegistrationForm() {
  const [form, setForm] = useState({ username: '', email: '', role: 'developer' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  return (
    <div>
      <input name="username" value={form.username} onChange={handleChange} placeholder="Username" />
      <input name="email" value={form.email} onChange={handleChange} placeholder="Email" />
      <p>Preview: {form.username} ({form.email})</p>
    </div>
  );
}""",
                    interactiveDemoType = "form",
                    quizQuestion = "What makes a form input 'controlled' in React?",
                    quizOptions = listOf(
                        "Its current value is tied to React state and updated through an onChange listener",
                        "It has a required attribute in the HTML markup",
                        "It communicates directly with a SQL database",
                        "It is wrapped in a <form> tag"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "A controlled input derives its value directly from React state and updates that state via change handlers."
                )
            )
        ),
        CurriculumLevel(
            level = 12,
            title = "React Hooks Overview & Rules",
            category = "Hooks Lab",
            description = "Why hooks exist, the 2 Golden Rules of Hooks, and an interactive index of all 13+ React hooks.",
            iconName = "link-2",
            lessons = listOf(
                Lesson(
                    id = "lvl12_l1",
                    levelNumber = 12,
                    title = "The Rules of Hooks",
                    subtitle = "Call hooks only at the top level & only from React functions",
                    durationMinutes = 15,
                    difficulty = "Intermediate",
                    keyTakeaways = listOf(
                        "Rule 1: Only call Hooks at the top level of your component (never inside loops, conditions, or nested functions)",
                        "Rule 2: Only call Hooks from React function components or custom Hooks",
                        "React relies on the call order of hooks remaining identical on every render"
                    ),
                    explanationMarkdown = "React tracks hooks using internal linked lists based on invocation order. If a hook is placed inside an `if` block, the order shifts between renders, corrupting state memory.",
                    visualizerType = VisualizerType.HOOKS_LIFECYCLE,
                    initialCode = """import React, { useState, useEffect } from 'react';

export default function CompliantHookDemo() {
  // Always call at the top level!
  const [count, setCount] = useState(0);
  const [name, setName] = useState('Awiskar');

  useEffect(() => {
    document.title = `${'$'}{name}: ${'$'}{count} clicks`;
  }, [count, name]);

  return <button onClick={() => setCount(c => c + 1)}>Clicks: {count}</button>;
}""",
                    interactiveDemoType = "counter",
                    quizQuestion = "Why are you forbidden from calling React hooks inside conditional `if` statements?",
                    quizOptions = listOf(
                        "React tracks state by the consistent order of hook calls between renders; conditionals break this order",
                        "JavaScript prohibits functions starting with 'use' inside blocks",
                        "If statements disable React's memory allocation",
                        "Conditions cause instant browser memory leaks"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "React internally associates hooks with a component based on their exact call order. Changing the call order across renders causes state mismatches."
                )
            )
        ),
        CurriculumLevel(
            level = 13,
            title = "useEffect Lab & Lifecycle Simulator",
            category = "Hooks Lab",
            description = "Mount, update, unmount lifecycle, side effects, dependency array secrets, cleanup functions, and preventing infinite loops.",
            iconName = "activity",
            lessons = listOf(
                Lesson(
                    id = "lvl13_l1",
                    levelNumber = 13,
                    title = "Mastering useEffect & Cleanups",
                    subtitle = "Mount → Side Effect → Dependency Change → Cleanup → Unmount",
                    durationMinutes = 20,
                    difficulty = "Intermediate",
                    keyTakeaways = listOf(
                        "No dependency array `[]`: runs on EVERY render",
                        "Empty dependency array `[]`: runs ONCE on component mount",
                        "With dependencies `[prop, state]`: runs when listed dependencies change",
                        "Return a cleanup function `return () => {}` to clear timers, sockets, and listeners"
                    ),
                    explanationMarkdown = "`useEffect` synchronizes your component with an external system (timers, network, DOM). The cleanup function runs before the component unmounts and before re-running the effect on dependency change.",
                    visualizerType = VisualizerType.HOOKS_LIFECYCLE,
                    initialCode = """import React, { useState, useEffect } from 'react';

export default function TimerDemo() {
  const [seconds, setSeconds] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setSeconds(prev => prev + 1);
    }, 1000);

    // CRUCIAL: Cleanup function prevents memory leak
    return () => clearInterval(timer);
  }, []); // Run once on mount

  return <div>Timer running: {seconds}s</div>;
}""",
                    interactiveDemoType = "effect_timer",
                    quizQuestion = "When does the cleanup function returned from `useEffect` run?",
                    quizOptions = listOf(
                        "Before the component unmounts, and before each re-execution of the effect when dependencies change",
                        "Only when the browser tab is closed",
                        "Immediately before the very first render",
                        "Only when an unhandled error occurs"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Cleanup functions run right before the component unmounts from the DOM, and also immediately before the effect is re-executed if dependencies changed."
                )
            )
        ),
        CurriculumLevel(
            level = 14,
            title = "useRef Lab & DOM Access",
            category = "Hooks Lab",
            description = "Referencing real DOM nodes, persistent mutable values without triggering re-renders, and tracking previous state.",
            iconName = "crosshair",
            lessons = listOf(
                Lesson(
                    id = "lvl14_l1",
                    levelNumber = 14,
                    title = "useRef: Persistent Reference Without Re-rendering",
                    subtitle = "DOM nodes focus, timer handles, and previous value tracking",
                    durationMinutes = 15,
                    difficulty = "Intermediate",
                    keyTakeaways = listOf(
                        "`useRef` returns a plain object `{ current: initialValue }`",
                        "Modifying `ref.current` does NOT trigger a component re-render",
                        "Attach `ref={inputRef}` to JSX elements to gain direct access to DOM methods like `.focus()`"
                    ),
                    explanationMarkdown = "Think of `useRef` as a 'box' that can hold any mutable value for the lifetime of the component. It is perfect for storing interval IDs, animation frames, and DOM node references.",
                    visualizerType = VisualizerType.HOOKS_LIFECYCLE,
                    initialCode = """import React, { useRef } from 'react';

export default function FocusInput() {
  const inputEl = useRef(null);

  const onButtonClick = () => {
    // Access the imperative DOM node directly
    inputEl.current?.focus();
  };

  return (
    <div>
      <input ref={inputEl} type="text" placeholder="I will get focused" />
      <button onClick={onButtonClick}>Focus Input</button>
    </div>
  );
}""",
                    interactiveDemoType = "form",
                    quizQuestion = "What is the key difference between updating `useState` and mutating `useRef`?",
                    quizOptions = listOf(
                        "Updating state triggers a re-render; mutating a ref does NOT trigger a re-render",
                        "useRef can only store strings while useState stores anything",
                        "useState is synchronous while useRef is asynchronous",
                        "useRef is destroyed after every render"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "useRef persists its .current value across renders without causing the component to re-execute, making it ideal for non-visual persistent data."
                )
            )
        ),
        CurriculumLevel(
            level = 15,
            title = "useContext & Global State",
            category = "Hooks Lab",
            description = "Solving prop drilling, creating Context, Context.Provider, consuming with useContext, and theme/auth providers.",
            iconName = "globe",
            lessons = listOf(
                Lesson(
                    id = "lvl15_l1",
                    levelNumber = 15,
                    title = "Avoiding Prop Drilling with useContext",
                    subtitle = "Provider → Component Tree → Consumer",
                    durationMinutes = 18,
                    difficulty = "Intermediate",
                    keyTakeaways = listOf(
                        "Context allows sharing data across the entire component tree without passing props at every level",
                        "`createContext()` initializes a context object with a default value",
                        "`useContext(MyContext)` subscribes any descendant component to the nearest Provider"
                    ),
                    explanationMarkdown = "Prop drilling happens when you pass a prop through 5 layers of components just so the 5th child can read it. Context lets children 'teleport' access to values provided higher up.",
                    visualizerType = VisualizerType.PROPS_FLOW,
                    initialCode = """import React, { createContext, useContext, useState } from 'react';

const ThemeContext = createContext('dark');

export function App() {
  const [theme, setTheme] = useState('dark');
  return (
    <ThemeContext.Provider value={{ theme, setTheme }}>
      <Toolbar />
    </ThemeContext.Provider>
  );
}

function Toolbar() {
  return <ThemeButton />;
}

function ThemeButton() {
  const { theme, setTheme } = useContext(ThemeContext);
  return (
    <button onClick={() => setTheme(t => t === 'dark' ? 'light' : 'dark')}>
      Current Theme: {theme}
    </button>
  );
}""",
                    interactiveDemoType = "props_demo",
                    quizQuestion = "What happens to components that call `useContext(MyContext)` when the Provider's value changes?",
                    quizOptions = listOf(
                        "All consuming components automatically re-render with the new context value",
                        "Only the parent Provider re-renders",
                        "The application throws a context mismatch warning",
                        "The browser refreshes the page"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Every component that consumes a context will automatically re-render whenever the value passed to the Provider changes."
                )
            )
        ),
        CurriculumLevel(
            level = 16,
            title = "useReducer & Reducer Pattern",
            category = "Hooks Lab",
            description = "State management for complex logic: Actions, Dispatch, Reducer function, State, and Shopping Cart lab.",
            iconName = "git-commit",
            lessons = listOf(
                Lesson(
                    id = "lvl16_l1",
                    levelNumber = 16,
                    title = "useReducer: Complex State State Machines",
                    subtitle = "Action Dispatch → Pure Reducer → Next State → UI Update",
                    durationMinutes = 18,
                    difficulty = "Intermediate",
                    keyTakeaways = listOf(
                        "`useReducer` is an alternative to `useState` for complex state with multiple sub-values",
                        "Reducers are pure functions: `(state, action) => newState`",
                        "Dispatching action objects `{ type: 'ADD_ITEM', payload: item }` decouples what happened from how state updates"
                    ),
                    explanationMarkdown = "`useReducer` gives you predictable state transitions. When several state variables change together or when state depends on complex conditions, useReducer keeps your code structured.",
                    visualizerType = VisualizerType.REDUCER_FLOW,
                    initialCode = """import React, { useReducer } from 'react';

function cartReducer(state, action) {
  switch (action.type) {
    case 'ADD':
      return { ...state, count: state.count + 1 };
    case 'REMOVE':
      return { ...state, count: Math.max(0, state.count - 1) };
    case 'RESET':
      return { count: 0 };
    default:
      return state;
  }
}

export default function CartCounter() {
  const [state, dispatch] = useReducer(cartReducer, { count: 0 });

  return (
    <div>
      <p>Cart Items: {state.count}</p>
      <button onClick={() => dispatch({ type: 'ADD' })}>+</button>
      <button onClick={() => dispatch({ type: 'REMOVE' })}>-</button>
    </div>
  );
}""",
                    interactiveDemoType = "reducer_cart",
                    quizQuestion = "Why must a reducer function be a pure function with no side effects?",
                    quizOptions = listOf(
                        "Given the same state and action, it must always return the exact same new state without mutating arguments",
                        "Reducers run in Web Workers where side effects are disabled",
                        "To prevent JavaScript from terminating the event loop",
                        "Because React reducers run on the backend server"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Pure functions are predictable, testable, and have zero side effects, allowing React to optimize rendering and support time-travel debugging."
                )
            )
        ),
        CurriculumLevel(
            level = 17,
            title = "Custom Hooks Builder",
            category = "Hooks Lab",
            description = "Extracting component logic into reusable functions: useFetch, useLocalStorage, useDebounce, useWindowSize, and useToggle.",
            iconName = "tool",
            lessons = listOf(
                Lesson(
                    id = "lvl17_l1",
                    levelNumber = 17,
                    title = "Building Custom React Hooks",
                    subtitle = "Reusing stateful logic across multiple components",
                    durationMinutes = 20,
                    difficulty = "Intermediate",
                    keyTakeaways = listOf(
                        "A custom hook is a JavaScript function whose name starts with `use` and calls other hooks",
                        "Custom hooks share STATEFUL LOGIC, not state itself (each component gets isolated state)",
                        "Examples: `useLocalStorage()`, `useDebounce()`, `useFetch()`"
                    ),
                    explanationMarkdown = "Custom hooks let you extract component logic into reusable functions. Instead of duplicating `useState` and `useEffect` across 10 screens, wrap it in a custom hook.",
                    visualizerType = VisualizerType.HOOKS_LIFECYCLE,
                    initialCode = """import { useState } from 'react';

// Custom Hook for boolean toggling
export function useToggle(initialValue = false) {
  const [state, setState] = useState(initialValue);
  const toggle = () => setState(prev => !prev);
  return [state, toggle];
}

// Consuming in any component:
export function ModalView() {
  const [isOpen, toggleOpen] = useToggle(false);
  return (
    <div>
      <button onClick={toggleOpen}>{isOpen ? 'Close' : 'Open'} Modal</button>
      {isOpen && <div className="modal">Modal Content</div>}
    </div>
  );
}""",
                    interactiveDemoType = "props_demo",
                    quizQuestion = "Do two components using the exact same custom hook share the same state values?",
                    quizOptions = listOf(
                        "No, each call to a custom hook creates completely isolated state for that component instance",
                        "Yes, custom hooks behave like global singletons",
                        "Yes, unless they are in different browser windows",
                        "Only if they share the same parent component"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Custom hooks reuse stateful logic, but every time a component calls the hook, it gets its own independent set of state variables."
                )
            )
        ),
        CurriculumLevel(
            level = 18,
            title = "React Routing & Route Visualizer",
            category = "Architecture",
            description = "Single Page Application (SPA) routing, BrowserRouter, Routes, Route, Link, useParams, and nested routes.",
            iconName = "compass",
            lessons = listOf(
                Lesson(
                    id = "lvl18_l1",
                    levelNumber = 18,
                    title = "Client-Side Routing in SPAs",
                    subtitle = "URL changes without page reloads, dynamic params, and navigation",
                    durationMinutes = 18,
                    difficulty = "Intermediate",
                    keyTakeaways = listOf(
                        "SPAs intercept link clicks and render components client-side without full-page reloads",
                        "`<Link to=\"/path\">` replaces regular `<a>` tags to prevent browser refreshes",
                        "`useParams()` extracts dynamic segment variables like `/users/:id`"
                    ),
                    explanationMarkdown = "In traditional websites, clicking a link requests a new HTML document from the server. In React SPAs, a router monitors the URL and renders matching component trees instantaneously.",
                    visualizerType = VisualizerType.ROUTE_STACK,
                    initialCode = """import { BrowserRouter, Routes, Route, Link, useParams } from 'react-router-dom';

function UserProfile() {
  const { username } = useParams();
  return <h2>Profile of: {username}</h2>;
}

export default function App() {
  return (
    <BrowserRouter>
      <nav>
        <Link to="/">Home</Link> | <Link to="/user/awiskar">Awiskar</Link>
      </nav>
      <Routes>
        <Route path="/" element={<h2>Home Page</h2>} />
        <Route path="/user/:username" element={<UserProfile />} />
      </Routes>
    </BrowserRouter>
  );
}""",
                    interactiveDemoType = "jsx_demo",
                    quizQuestion = "Why should you use React Router's `<Link>` instead of `<a href=\"...\">`?",
                    quizOptions = listOf(
                        "Link updates the browser history and renders routes without triggering a full page reload",
                        "Link encodes HTML entities automatically",
                        "The <a> tag is deprecated in HTML5",
                        "Link is required for browser cookie persistence"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "The Link component intercepts the click event, updates the browser URL via the History API, and renders the matched route without dropping application state."
                )
            )
        ),
        CurriculumLevel(
            level = 19,
            title = "API Integration Lab",
            category = "Architecture",
            description = "REST APIs, HTTP methods (GET, POST, PUT, DELETE), JSON formatting, fetch vs Axios, and request/response inspection.",
            iconName = "server",
            lessons = listOf(
                Lesson(
                    id = "lvl19_l1",
                    levelNumber = 19,
                    title = "Connecting React to REST APIs",
                    subtitle = "React App → Request → REST Server → Response JSON → State → UI",
                    durationMinutes = 18,
                    difficulty = "Intermediate",
                    keyTakeaways = listOf(
                        "`fetch(url)` returns a Promise that resolves to an HTTP Response object",
                        "Always check `response.ok` before calling `response.json()`",
                        "Store API data in state and track loading and error states explicitly"
                    ),
                    explanationMarkdown = "React is a UI library; it connects to backends via HTTP. A robust API call always handles three distinct states: Loading (skeleton/spinner), Success (render data), and Error (retry banner).",
                    visualizerType = VisualizerType.API_TIMELINE,
                    initialCode = """import React, { useState, useEffect } from 'react';

export default function UserList() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch('https://jsonplaceholder.typicode.com/users')
      .then(res => res.json())
      .then(data => {
        setUsers(data);
        setLoading(false);
      })
      .catch(err => setLoading(false));
  }, []);

  if (loading) return <p>Loading users...</p>;

  return (
    <ul>
      {users.map(u => <li key={u.id}>{u.name} ({u.email})</li>)}
    </ul>
  );
}""",
                    interactiveDemoType = "api_fetch",
                    quizQuestion = "Why is it important to track a `loading` state during asynchronous data fetching?",
                    quizOptions = listOf(
                        "To provide immediate visual feedback and prevent crashes from rendering undefined data",
                        "To prevent the browser from shutting down network requests",
                        "Because React throws an error if an API call takes longer than 1 second",
                        "To automatically cache the response in localStorage"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "While a network request is in flight, the data does not yet exist. A loading state gives user feedback and prevents accessing undefined properties."
                )
            )
        ),
        CurriculumLevel(
            level = 20,
            title = "Data Fetching & Caching Patterns",
            category = "Architecture",
            description = "Skeletons, error boundaries, optimistic updates, pagination, infinite scroll, and React Query / TanStack Query concepts.",
            iconName = "download-cloud",
            lessons = listOf(
                Lesson(
                    id = "lvl20_l1",
                    levelNumber = 20,
                    title = "Advanced Fetching: Caching & Optimistic UI",
                    subtitle = "Client state vs Server state, query caching, and optimistic updates",
                    durationMinutes = 18,
                    difficulty = "Intermediate",
                    keyTakeaways = listOf(
                        "Server state is remote, asynchronous, and requires caching, invalidation, and background refetching",
                        "Optimistic UI updates local state immediately before the network response confirms success",
                        "Tools like TanStack Query replace manual useEffect boilerplate for server state"
                    ),
                    explanationMarkdown = "Client state (modal open/closed) is fundamentally different from Server state (posts, user list). Mixing them in raw `useEffect` leads to race conditions and caching headaches.",
                    visualizerType = VisualizerType.API_TIMELINE,
                    initialCode = """// Optimistic Update pattern
const handleLike = async (postId) => {
  // 1. Instantly update UI optimistically
  setLikes(prev => prev + 1);

  try {
    // 2. Fire actual server mutation
    await api.post(`/posts/${'$'}{postId}/like`);
  } catch (error) {
    // 3. Rollback if server request fails
    setLikes(prev => prev - 1);
    showToast('Failed to like post.');
  }
};""",
                    interactiveDemoType = "api_fetch",
                    quizQuestion = "What is an 'optimistic UI update'?",
                    quizOptions = listOf(
                        "Updating the UI immediately under the assumption the server request will succeed, rolling back if it fails",
                        "Always displaying a positive success message even on errors",
                        "Skipping network requests completely in mobile browsers",
                        "Pre-rendering all possible future pages at build time"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Optimistic UI makes apps feel instantaneous by reflecting user actions immediately on screen while the background network mutation completes."
                )
            )
        ),
        CurriculumLevel(
            level = 21,
            title = "State Management Academy",
            category = "Architecture",
            description = "When to use Local state, Lifted state, Context, Zustand, Redux Toolkit, and distinguishing client vs server state.",
            iconName = "git-pull-request",
            lessons = listOf(
                Lesson(
                    id = "lvl21_l1",
                    levelNumber = 21,
                    title = "Choosing the Right State Solution",
                    subtitle = "Local → Lifted → Context → Zustand / Redux → TanStack Query",
                    durationMinutes = 20,
                    difficulty = "Advanced",
                    keyTakeaways = listOf(
                        "Default to local state (`useState`); lift state to common parents only when shared",
                        "Use Context for rarely changing global values (theme, current auth user, locale)",
                        "Use dedicated stores (Zustand/Redux) for complex, high-frequency client state",
                        "Use server-state libraries for remote data fetching"
                    ),
                    explanationMarkdown = "Over-engineering state management is one of the most common mistakes in React. Start with local state, and only escalate when necessary.",
                    visualizerType = VisualizerType.REDUCER_FLOW,
                    initialCode = """// Minimal modern Zustand store example
import create from 'zustand';

export const useStore = create((set) => ({
  cart: [],
  addItem: (item) => set((state) => ({ cart: [...state.cart, item] })),
  clearCart: () => set({ cart: [] }),
}));""",
                    interactiveDemoType = "reducer_cart",
                    quizQuestion = "Which scenario is most appropriate for React Context rather than a heavy global store?",
                    quizOptions = listOf(
                        "Low-frequency updates like current theme (dark/light) or user locale",
                        "60fps high-frequency mouse tracking coordinates",
                        "A database containing 10,000 live streaming rows",
                        "Temporary text typed into a single search input"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Context is ideal for low-frequency global settings like theme and authenticated user. For high-frequency state, Context can cause unwanted re-renders across consumers."
                )
            )
        ),
        CurriculumLevel(
            level = 22,
            title = "Component Design & UI Lab",
            category = "Architecture",
            description = "Designing modern design system components: Buttons, Cards, Modals, Tabs, Accordions, Toasts, and Badges.",
            iconName = "package",
            lessons = listOf(
                Lesson(
                    id = "lvl22_l1",
                    levelNumber = 22,
                    title = "Design Systems & Component Variants",
                    subtitle = "Building composable, accessible buttons, modals, and tabs",
                    durationMinutes = 18,
                    difficulty = "Intermediate",
                    keyTakeaways = listOf(
                        "Design components around variant props: `variant=\"primary\" | \"secondary\" | \"danger\"`",
                        "Forward standard HTML props using rest parameters `...rest`",
                        "Separate styling, structure, and accessibility attributes cleanly"
                    ),
                    explanationMarkdown = "A well-designed component library lets developers assemble cohesive interfaces in minutes. In our Component Lab, you can test buttons, cards, and modal builders live.",
                    visualizerType = VisualizerType.COMPONENT_TREE,
                    initialCode = """export function Button({ variant = 'primary', size = 'md', children, ...props }) {
  const baseClass = 'btn';
  const variantClass = `btn-${'$'}{variant}`;
  const sizeClass = `btn-${'$'}{size}`;

  return (
    <button className={`${'$'}{baseClass} ${'$'}{variantClass} ${'$'}{sizeClass}`} {...props}>
      {children}
    </button>
  );
}""",
                    interactiveDemoType = "props_demo",
                    quizQuestion = "Why is it best practice to pass `{...props}` to the root HTML element in reusable components?",
                    quizOptions = listOf(
                        "It allows callers to pass native attributes like onClick, aria-label, and disabled without manually declaring them all",
                        "It makes the component render on the server",
                        "It converts the element into a React Portal",
                        "It prevents all re-renders automatically"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Prop spreading on the underlying element ensures the custom component supports all standard native HTML attributes transparently."
                )
            )
        ),
        CurriculumLevel(
            level = 23,
            title = "Component Architecture & Patterns",
            category = "Architecture",
            description = "Compound components, container/presentational pattern, render props, and component composition.",
            iconName = "layout",
            lessons = listOf(
                Lesson(
                    id = "lvl23_l1",
                    levelNumber = 23,
                    title = "Compound Components & Composition",
                    subtitle = "Building flexible UIs like <Select><Select.Option /></Select>",
                    durationMinutes = 20,
                    difficulty = "Advanced",
                    keyTakeaways = listOf(
                        "Compound components work together to form a cohesive unit with implicit state sharing",
                        "Examples include HTML `<select>` + `<option>`, and React `<Tabs>` + `<Tab>`",
                        "Composition (`children`) is generally superior to deep inheritance or mega-prop configs"
                    ),
                    explanationMarkdown = "Compound components share state internally via Context so that consumers can arrange UI elements flexibly without passing dozens of coordination props.",
                    visualizerType = VisualizerType.COMPONENT_TREE,
                    initialCode = """// Compound Component pattern
<Tabs defaultIndex={0}>
  <Tabs.List>
    <Tabs.Tab index={0}>Overview</Tabs.Tab>
    <Tabs.Tab index={1}>Code</Tabs.Tab>
  </Tabs.List>
  <Tabs.Panel index={0}>Overview content here</Tabs.Panel>
  <Tabs.Panel index={1}>Code snippet here</Tabs.Panel>
</Tabs>""",
                    interactiveDemoType = "component_tree_demo",
                    quizQuestion = "What is the primary benefit of the Compound Component pattern?",
                    quizOptions = listOf(
                        "It provides a flexible, expressive API where sub-components share state implicitly without messy prop passing",
                        "It reduces the size of your JavaScript bundle by 50%",
                        "It eliminates the need for CSS",
                        "It turns React components into backend endpoints"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Compound components let consumers organize UI markup naturally while keeping shared state encapsulated."
                )
            )
        ),
        CurriculumLevel(
            level = 24,
            title = "React Performance Lab",
            category = "Advanced",
            description = "React rendering model, React.memo, useMemo, useCallback, virtualized lists, bundle splitting, and Render Monitor.",
            iconName = "cpu",
            lessons = listOf(
                Lesson(
                    id = "lvl24_l1",
                    levelNumber = 24,
                    title = "Memoization & Render Optimization",
                    subtitle = "React.memo, useMemo, and useCallback explained clearly",
                    durationMinutes = 22,
                    difficulty = "Advanced",
                    keyTakeaways = listOf(
                        "By default, when a parent component renders, all its children re-render recursively",
                        "`React.memo(Component)` skips re-renders if props have not changed (shallow comparison)",
                        "`useCallback` caches a function definition between renders",
                        "`useMemo` caches the result of an expensive calculation"
                    ),
                    explanationMarkdown = "Premature optimization is harmful, but understanding memoization is crucial. Use `useCallback` when passing callbacks to optimized children wrapped in `React.memo`.",
                    visualizerType = VisualizerType.VIRTUAL_DOM_DIFF,
                    initialCode = """import React, { useState, useMemo, useCallback } from 'react';

const Child = React.memo(({ onClick, label }) => {
  console.log('Child rendered:', label);
  return <button onClick={onClick}>{label}</button>;
});

export default function MemoDemo() {
  const [count, setCount] = useState(0);

  // useCallback prevents Child from re-rendering when count changes
  const handleClick = useCallback(() => {
    alert('Clicked!');
  }, []);

  return (
    <div>
      <p>Parent Count: {count}</p>
      <button onClick={() => setCount(c => c + 1)}>Increment</button>
      <Child label="Stable Button" onClick={handleClick} />
    </div>
  );
}""",
                    interactiveDemoType = "counter",
                    quizQuestion = "Why does passing an inline arrow function `onClick={() => doSomething()}` to a `React.memo` child cause it to re-render anyway?",
                    quizOptions = listOf(
                        "A new function reference is created in memory on every render, failing shallow equality comparison (prevProps !== nextProps)",
                        "Arrow functions are not supported by React.memo",
                        "React.memo only works with numeric props",
                        "Inline functions trigger automatic garbage collection"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Every time the parent component renders, inline arrow functions are recreated as fresh references. Wrapping the function in useCallback preserves reference equality."
                )
            )
        ),
        CurriculumLevel(
            level = 25,
            title = "React Debugging Studio",
            category = "Advanced",
            description = "Diagnosing broken React apps: infinite loops, stale closures, missing key warnings, and state mutation bugs.",
            iconName = "alert-circle",
            lessons = listOf(
                Lesson(
                    id = "lvl25_l1",
                    levelNumber = 25,
                    title = "Common React Bugs & How to Fix Them",
                    subtitle = "Infinite useEffect loops, state mutations, and stale closures",
                    durationMinutes = 20,
                    difficulty = "Advanced",
                    keyTakeaways = listOf(
                        "Infinite loops occur when useEffect updates state that is also listed in its dependencies without guard conditions",
                        "Mutating state directly (`state.push(item)`) bypasses React's change detection",
                        "Stale closures occur when an effect or callback captures an outdated state variable"
                    ),
                    explanationMarkdown = "In our React Debugging Studio, you can examine broken code snippets, inspect the simulated error console and component tree, and test fixes.",
                    visualizerType = VisualizerType.HOOKS_LIFECYCLE,
                    initialCode = """// BUGGY CODE: Infinite Loop!
function BrokenComponent() {
  const [data, setData] = useState(null);

  // BUG: Updating state inside useEffect without dependencies creates infinite loop!
  useEffect(() => {
    fetch('/api/user')
      .then(res => res.json())
      .then(json => setData(json));
  }); // MISSING DEPENDENCY ARRAY! FIX: Add []

  return <div>{data?.name}</div>;
}""",
                    interactiveDemoType = "jsx_demo",
                    quizQuestion = "Why does omitting the dependency array in `useEffect(() => { setState(...) })` cause an infinite loop?",
                    quizOptions = listOf(
                        "The effect runs on every render, updates state, which triggers a new render, which runs the effect again indefinitely",
                        "The browser crashes because of missing memory",
                        "React disables asynchronous operations without dependencies",
                        "JavaScript garbage collection cannot reclaim the state"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Without a dependency array, useEffect runs after EVERY render. Setting state inside it forces another render, creating an endless cycle."
                )
            )
        ),
        CurriculumLevel(
            level = 26,
            title = "Error Boundaries & Resilience",
            category = "Advanced",
            description = "Preventing full app white screens, componentDidCatch, fallback UIs, and handling runtime rendering errors.",
            iconName = "shield",
            lessons = listOf(
                Lesson(
                    id = "lvl26_l1",
                    levelNumber = 26,
                    title = "Error Boundaries in React",
                    subtitle = "Catching JavaScript rendering errors and displaying fallback UI",
                    durationMinutes = 15,
                    difficulty = "Intermediate",
                    keyTakeaways = listOf(
                        "Error boundaries catch errors during rendering, lifecycle methods, and constructors in their child tree",
                        "They prevent the entire React component tree from unmounting into a blank white screen",
                        "Error boundaries do NOT catch errors inside event handlers, asynchronous code, or server-side rendering"
                    ),
                    explanationMarkdown = "In React 16+, a JavaScript error inside any part of the UI that is not caught by an error boundary will unmount the entire React component tree.",
                    visualizerType = VisualizerType.COMPONENT_TREE,
                    initialCode = """class ErrorBoundary extends React.Component {
  state = { hasError: false };

  static getDerivedStateFromError(error) {
    return { hasError: true };
  }

  componentDidCatch(error, errorInfo) {
    console.error('Error caught by boundary:', error, errorInfo);
  }

  render() {
    if (this.state.hasError) {
      return <h2>Something went wrong in this section.</h2>;
    }
    return this.props.children;
  }
}""",
                    interactiveDemoType = "jsx_demo",
                    quizQuestion = "Which type of errors are NOT caught by React Error Boundaries?",
                    quizOptions = listOf(
                        "Errors inside asynchronous code (e.g. setTimeout, fetch) and event handlers",
                        "Errors thrown during component render",
                        "Errors in child component constructors",
                        "Errors in child lifecycle methods"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Error Boundaries only catch errors in rendering and lifecycle methods. For event handlers and async fetch calls, use standard try/catch blocks."
                )
            )
        ),
        CurriculumLevel(
            level = 27,
            title = "Testing React Applications",
            category = "Advanced",
            description = "Unit testing, React Testing Library philosophy, userEvent, mocking API calls, and simulated test execution.",
            iconName = "check-circle",
            lessons = listOf(
                Lesson(
                    id = "lvl27_l1",
                    levelNumber = 27,
                    title = "React Testing Library Fundamentals",
                    subtitle = "Test components the way real users interact with them",
                    durationMinutes = 18,
                    difficulty = "Advanced",
                    keyTakeaways = listOf(
                        "React Testing Library philosophy: test behavior and DOM output, not internal implementation details",
                        "Prefer queries by accessible role: `screen.getByRole('button', { name: /submit/i })`",
                        "`userEvent` simulates real keyboard and pointer actions realistically"
                    ),
                    explanationMarkdown = "The more your tests resemble the way your software is used, the more confidence they can give you. Don't test state variables directly; test what the user sees and clicks.",
                    visualizerType = VisualizerType.NONE,
                    initialCode = """import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import Counter from './Counter';

test('increments counter when button is clicked', async () => {
  render(<Counter />);

  const button = screen.getByRole('button', { name: /add 1/i });
  expect(screen.getByText(/Current Count: 0/i)).toBeInTheDocument();

  await userEvent.click(button);

  expect(screen.getByText(/Current Count: 1/i)).toBeInTheDocument();
});""",
                    interactiveDemoType = "counter",
                    quizQuestion = "What is the primary guiding principle of React Testing Library?",
                    quizOptions = listOf(
                        "The more your tests resemble the way your software is used, the more confidence they can give you",
                        "Always test internal state variables directly before testing UI",
                        "Never mock network requests under any circumstance",
                        "Unit tests must be written exclusively in TypeScript"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "React Testing Library encourages testing accessible output and user interactions rather than private component implementation details."
                )
            )
        ),
        CurriculumLevel(
            level = 28,
            title = "Accessibility (a11y) in React",
            category = "Advanced",
            description = "Semantic HTML, keyboard navigation, focus management with refs, ARIA roles, and accessible dialogs.",
            iconName = "eye",
            lessons = listOf(
                Lesson(
                    id = "lvl28_l1",
                    levelNumber = 28,
                    title = "Building Accessible React Apps",
                    subtitle = "Semantic elements, ARIA attributes, and keyboard traps",
                    durationMinutes = 16,
                    difficulty = "Intermediate",
                    keyTakeaways = listOf(
                        "Always use semantic HTML `<button>` instead of `<div onClick>`",
                        "Ensure interactive elements have accessible names and focus indicators",
                        "Manage focus explicitly when opening/closing modals using refs"
                    ),
                    explanationMarkdown = "Accessibility is not optional. Building with accessible components ensures everyone, including screen-reader and keyboard-only users, can use your applications.",
                    visualizerType = VisualizerType.NONE,
                    initialCode = """export function IconButton({ icon, label, onClick }) {
  return (
    <button 
      onClick={onClick} 
      aria-label={label} // Critical for screen readers!
      className="icon-btn"
    >
      <span aria-hidden="true">{icon}</span>
    </button>
  );
}""",
                    interactiveDemoType = "props_demo",
                    quizQuestion = "Why should you use an `<button>` tag instead of a `<div onClick={...}>` for clickable actions?",
                    quizOptions = listOf(
                        "Native buttons automatically provide keyboard focus, Enter/Space activation, and screen reader announcements",
                        "Divs cannot receive click events in modern browsers",
                        "Buttons are faster to render in the Virtual DOM",
                        "CSS cannot be applied to clickable divs"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Native buttons include built-in keyboard navigation (Tab, Enter, Space) and accessibility semantics for assistive technologies without custom JS hacks."
                )
            )
        ),
        CurriculumLevel(
            level = 29,
            title = "TypeScript with React",
            category = "Advanced",
            description = "Typing props, state, event handlers, generics, React.FC vs plain functions, and typing API responses.",
            iconName = "file-text",
            lessons = listOf(
                Lesson(
                    id = "lvl29_l1",
                    levelNumber = 29,
                    title = "Type-Safe React with TypeScript",
                    subtitle = "Interfaces, Prop types, Event handlers, and Generics",
                    durationMinutes = 20,
                    difficulty = "Advanced",
                    keyTakeaways = listOf(
                        "Define interfaces or types for component props: `interface CardProps { title: string; count?: number; }`",
                        "Use React's built-in event types: `React.ChangeEvent<HTMLInputElement>`, `React.FormEvent`",
                        "TypeScript catches invalid props and missing properties at compile time"
                    ),
                    explanationMarkdown = "TypeScript brings compile-time safety and instant IDE autocomplete to React. It eliminates runtime `undefined is not a function` errors.",
                    visualizerType = VisualizerType.PROPS_FLOW,
                    initialCode = """import React, { useState } from 'react';

interface UserCardProps {
  name: string;
  age: number;
  role: 'admin' | 'member';
  onSelect?: (name: string) => void;
}

export const UserCard: React.FC<UserCardProps> = ({ name, age, role, onSelect }) => {
  return (
    <div onClick={() => onSelect?.(name)}>
      <h3>{name} ({age})</h3>
      <span>Role: {role}</span>
    </div>
  );
};""",
                    interactiveDemoType = "props_demo",
                    quizQuestion = "Which type should you use for an input element's `onChange` event in TypeScript?",
                    quizOptions = listOf(
                        "React.ChangeEvent<HTMLInputElement>",
                        "Event",
                        "MouseEvent<HTMLInputElement>",
                        "Function"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "React provides generic ChangeEvent types parameterized with the specific HTML element (HTMLInputElement) for full typed access to e.target.value."
                )
            )
        ),
        CurriculumLevel(
            level = 30,
            title = "Next.js & Server Components Awareness",
            category = "Advanced",
            description = "React vs Next.js, Server vs Client components ('use client'), SSR vs SSG, and file-based App Router.",
            iconName = "cpu",
            lessons = listOf(
                Lesson(
                    id = "lvl30_l1",
                    levelNumber = 30,
                    title = "Modern Full-Stack React & Next.js",
                    subtitle = "React Server Components (RSC), 'use client', and Static Generation",
                    durationMinutes = 18,
                    difficulty = "Advanced",
                    keyTakeaways = listOf(
                        "React Server Components execute exclusively on the server and send zero JavaScript to the client",
                        "Add `'use client'` at the top of a file when you need hooks (`useState`, `useEffect`) or browser events",
                        "Next.js provides hybrid rendering: Server-Side Rendering (SSR), Static Generation (SSG), and API routes"
                    ),
                    explanationMarkdown = "React is the foundation; frameworks like Next.js add file-based routing, SEO optimization, and server execution. Understanding when to use Server vs Client components is the modern React standard.",
                    visualizerType = VisualizerType.NONE,
                    initialCode = """// Server Component (Default in Next.js App Router)
// Fetches directly from database on server, zero JS bundle cost!
export default async function UserProfile({ params }) {
  const user = await db.user.findUnique({ where: { id: params.id } });

  return (
    <div>
      <h1>{user.name}</h1>
      {/* Interactive client component imported inside server tree */}
      <FollowButton userId={user.id} />
    </div>
  );
}""",
                    interactiveDemoType = "jsx_demo",
                    quizQuestion = "When MUST you add the `'use client'` directive in Next.js App Router?",
                    quizOptions = listOf(
                        "Whenever the component uses React hooks (useState, useEffect) or browser event listeners (onClick, onChange)",
                        "In every single component file",
                        "Only when fetching data with fetch()",
                        "Only in the root layout.tsx file"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Components with interactive browser behavior, state, hooks, or event listeners belong in the client bundle and must be marked with 'use client'."
                )
            )
        ),
        CurriculumLevel(
            level = 31,
            title = "Professional React Architecture",
            category = "Architecture",
            description = "Production folder structures, feature-based architecture, API layer separation, custom hook conventions, and clean code.",
            iconName = "award",
            lessons = listOf(
                Lesson(
                    id = "lvl31_l1",
                    levelNumber = 31,
                    title = "Production Architecture & Clean Code",
                    subtitle = "Scalable folder layouts, naming standards, and architectural boundaries",
                    durationMinutes = 20,
                    difficulty = "Advanced",
                    keyTakeaways = listOf(
                        "Organize by feature (e.g. `features/auth/`, `features/checkout/`) for large apps",
                        "Separate UI components from API calls and business logic hooks",
                        "Keep components small, single-purpose, and rigorously tested"
                    ),
                    explanationMarkdown = "Professional React development is about long-term maintainability. Clean architecture allows teams of 20+ engineers to work on a codebase without stepping on each other's toes.",
                    visualizerType = VisualizerType.COMPONENT_TREE,
                    initialCode = """/* Recommended Feature-Based Architecture:
src/
 ├── components/       # Shared UI primitives (Button, Modal, Input)
 ├── features/         # Feature modules
 │    ├── auth/        # api, components, hooks, types
 │    └── dashboard/   # api, components, hooks, types
 ├── hooks/            # Global custom hooks
 ├── services/         # HTTP client & API layer
 └── utils/            # Helper functions
*/""",
                    interactiveDemoType = "component_tree_demo",
                    quizQuestion = "What is the primary advantage of feature-based folder organization over grouping by file type?",
                    quizOptions = listOf(
                        "Related components, hooks, types, and API calls reside together, making features easy to find, modify, and delete",
                        "It makes the compiled bundle 3x smaller",
                        "It eliminates all import statements",
                        "It makes React run in parallel threads"
                    ),
                    quizCorrectIndex = 0,
                    quizExplanation = "Feature-based organization encapsulates all files belonging to a specific domain together, preventing developer fatigue navigating massive flat component folders."
                )
            )
        )
    )

    fun getLevel(levelNumber: Int): CurriculumLevel? = allLevels.find { it.level == levelNumber }
    fun getLesson(lessonId: String): Lesson? = allLevels.flatMap { it.lessons }.find { it.id == lessonId }
}
