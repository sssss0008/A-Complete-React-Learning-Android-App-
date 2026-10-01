package com.example.data.repository

import com.example.data.model.Challenge
import com.example.data.model.ChallengeType
import com.example.data.model.TestCase

object ChallengeData {

    val allChallenges: List<Challenge> = listOf(
        Challenge(
            id = "ch_01",
            title = "Fix Mutated State Array",
            difficulty = "Beginner",
            category = "State",
            type = ChallengeType.FIX_BUG,
            problemStatement = "The developer is trying to add a new task to the list, but clicking the 'Add Task' button does not update the screen! Identify and fix the direct state mutation bug.",
            requirements = listOf(
                "Do not use `tasks.push(newTask)` directly on state",
                "Update state using an immutable array copy `[...tasks, newTask]`",
                "Ensure clicking 'Add Task' updates the rendered list"
            ),
            starterCode = """function TaskList() {
  const [tasks, setTasks] = React.useState(['Learn JS', 'Learn React']);
  const [input, setInput] = React.useState('');

  const addTask = () => {
    if (!input.trim()) return;
    // ❌ BUG HERE: Direct state mutation!
    tasks.push(input);
    setTasks(tasks); 
    setInput('');
  };

  return (
    <div>
      <input value={input} onChange={e => setInput(e.target.value)} />
      <button onClick={addTask}>Add Task</button>
      <ul>{tasks.map((t, i) => <li key={i}>{t}</li>)}</ul>
    </div>
  );
}""",
            solutionCode = """function TaskList() {
  const [tasks, setTasks] = React.useState(['Learn JS', 'Learn React']);
  const [input, setInput] = React.useState('');

  const addTask = () => {
    if (!input.trim()) return;
    // ✅ FIXED: Create a fresh array with spread operator
    setTasks(prev => [...prev, input]);
    setInput('');
  };

  return (
    <div>
      <input value={input} onChange={e => setInput(e.target.value)} />
      <button onClick={addTask}>Add Task</button>
      <ul>{tasks.map((t, i) => <li key={i}>{t}</li>)}</ul>
    </div>
  );
}""",
            hint = "React checks for state updates using shallow reference equality (`prev === next`). If you mutate the array in place, the reference doesn't change, so React skips re-rendering.",
            explanation = "Using `tasks.push()` mutates the existing array reference. Passing the same array back to `setTasks(tasks)` causes React to compare `tasks === tasks` (true) and bail out of rendering.",
            testCases = listOf(
                TestCase("Does not mutate original array", "Passes immutability check", true),
                TestCase("Adds item to list", "Rendered items length increments", true),
                TestCase("Clears input field after adding", "input is reset to empty string", true)
            ),
            initialErrorLog = "Warning: State was mutated in-place. React detected no change in array reference and skipped component re-render."
        ),
        Challenge(
            id = "ch_02",
            title = "Stop Infinite useEffect Loop",
            difficulty = "Intermediate",
            category = "Hooks",
            type = ChallengeType.FIX_BUG,
            problemStatement = "The UserProfile component crashes the browser tab because it enters an infinite re-render loop fetching user data. Fix the useEffect hook.",
            requirements = listOf(
                "Add the correct dependency array to `useEffect`",
                "Ensure the API request only runs when the `userId` prop changes",
                "Prevent endless re-renders"
            ),
            starterCode = """function UserProfile({ userId }) {
  const [user, setUser] = React.useState(null);

  // ❌ BUG: No dependency array! Runs after EVERY render!
  React.useEffect(() => {
    fetchUserData(userId).then(data => {
      setUser(data);
    });
  });

  return <div>{user ? user.name : 'Loading...'}</div>;
}""",
            solutionCode = """function UserProfile({ userId }) {
  const [user, setUser] = React.useState(null);

  // ✅ FIXED: Only re-run effect when userId changes!
  React.useEffect(() => {
    let isMounted = true;
    fetchUserData(userId).then(data => {
      if (isMounted) setUser(data);
    });
    return () => { isMounted = false; };
  }, [userId]);

  return <div>{user ? user.name : 'Loading...'}</div>;
}""",
            hint = "If you omit the second argument of `useEffect`, it executes after every single render. Since `setUser` triggers a render, an infinite loop occurs.",
            explanation = "Providing `[userId]` ensures the effect executes once on mount and subsequently only if `userId` has changed.",
            testCases = listOf(
                TestCase("Runs on initial mount", "Data is fetched on component load", true),
                TestCase("Does not re-run indefinitely", "Render count stays at 2 (mount + data)", true),
                TestCase("Re-fetches on userId change", "Fetches new user when prop updates", true)
            ),
            initialErrorLog = "Error: Maximum update depth exceeded. This can happen when a component repeatedly calls setState inside useEffect."
        ),
        Challenge(
            id = "ch_03",
            title = "Write a useToggle Custom Hook",
            difficulty = "Beginner",
            category = "Hooks",
            type = ChallengeType.WRITE_HOOK,
            problemStatement = "Write a custom hook called `useToggle` that manages a boolean state. It should accept an initial boolean (defaulting to false) and return a tuple `[value, toggle]` where `toggle` flips the boolean value.",
            requirements = listOf(
                "Accept optional `initialValue = false`",
                "Return a tuple with `[state, toggleFunction]`",
                "Calling `toggleFunction()` should invert the current state"
            ),
            starterCode = """// Implement useToggle below:
function useToggle(initialValue = false) {
  // TODO: Add state and toggle handler
}

// Example usage:
// const [isOn, toggleIsOn] = useToggle(false);""",
            solutionCode = """function useToggle(initialValue = false) {
  const [value, setValue] = React.useState(initialValue);
  const toggle = React.useCallback(() => {
    setValue(prev => !prev);
  }, []);
  return [value, toggle];
}""",
            hint = "Use `useState(initialValue)` and write a helper function that calls `setValue(prev => !prev)`.",
            explanation = "Custom hooks extract stateful patterns so you don't have to rewrite the same boolean toggling logic across dialogs, accordions, and switches.",
            testCases = listOf(
                TestCase("Initializes with default false", "Value is false", true),
                TestCase("Toggle flips false to true", "Value becomes true", true),
                TestCase("Toggle flips true back to false", "Value becomes false", true)
            )
        ),
        Challenge(
            id = "ch_04",
            title = "Missing Key in List Items",
            difficulty = "Beginner",
            category = "JSX",
            type = ChallengeType.FIX_BUG,
            problemStatement = "React emits a console warning: 'Each child in a list should have a unique key prop'. Fix the list rendering to include a unique and stable key.",
            requirements = listOf(
                "Add the `key` prop to the top-level element returned inside the `.map()` callback",
                "Use `product.id` rather than the array index for stability"
            ),
            starterCode = """function ProductList({ products }) {
  return (
    <div className="catalog">
      {products.map(product => (
        // ❌ BUG: Missing key prop!
        <div className="product-card">
          <h4>{product.name}</h4>
          <p>${'$'}{product.price}</p>
        </div>
      ))}
    </div>
  );
}""",
            solutionCode = """function ProductList({ products }) {
  return (
    <div className="catalog">
      {products.map(product => (
        // ✅ FIXED: Added key={product.id}
        <div key={product.id} className="product-card">
          <h4>{product.name}</h4>
          <p>${'$'}{product.price}</p>
        </div>
      ))}
    </div>
  );
}""",
            hint = "Add `key={product.id}` to the outermost `<div className='product-card'>`.",
            explanation = "Keys give React an identifier for each element so it can preserve component state and avoid re-rendering entire lists when items change position.",
            testCases = listOf(
                TestCase("Eliminates missing key warning", "Console is clean", true),
                TestCase("Uses stable product.id", "Key matches item identity", true)
            ),
            initialErrorLog = "Warning: Each child in a list should have a unique 'key' prop. Check the render method of ProductList."
        ),
        Challenge(
            id = "ch_05",
            title = "Async Data Fetching with Error Handling",
            difficulty = "Intermediate",
            category = "APIs",
            type = ChallengeType.FETCH_API,
            problemStatement = "Create an API data fetcher component that handles all 3 states: Loading indicator, successful data rendering, and friendly error alert with a retry button.",
            requirements = listOf(
                "Show 'Loading articles...' while the request is in flight",
                "Render list of articles upon success",
                "Display error message and 'Retry' button if the request fails"
            ),
            starterCode = """function ArticleViewer() {
  const [data, setData] = React.useState(null);
  const [loading, setLoading] = React.useState(true);
  const [error, setError] = React.useState(null);

  // TODO: Fetch from '/api/articles' with try/catch
  return <div>Implement viewer</div>;
}""",
            solutionCode = """function ArticleViewer() {
  const [articles, setArticles] = React.useState([]);
  const [loading, setLoading] = React.useState(true);
  const [error, setError] = React.useState(null);

  const fetchArticles = async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await fetch('/api/articles');
      if (!res.ok) throw new Error('Failed to load articles');
      const data = await res.json();
      setArticles(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  React.useEffect(() => { fetchArticles(); }, []);

  if (loading) return <p>Loading articles...</p>;
  if (error) return (
    <div>
      <p style={{ color: 'red' }}>Error: {error}</p>
      <button onClick={fetchArticles}>Retry</button>
    </div>
  );

  return (
    <ul>
      {articles.map(a => <li key={a.id}>{a.title}</li>)}
    </ul>
  );
}""",
            hint = "Wrap the fetch call in a `try...catch...finally` block and update loading/error state appropriately.",
            explanation = "Handling all 3 network states is mandatory in production React applications to give users responsive visual feedback.",
            testCases = listOf(
                TestCase("Renders loading state initially", "Shows loading UI", true),
                TestCase("Displays data on success", "Articles list rendered", true),
                TestCase("Displays error banner on network failure", "Error UI with retry displayed", true)
            )
        )
    )

    fun getChallenge(id: String): Challenge? = allChallenges.find { it.id == id }
}
