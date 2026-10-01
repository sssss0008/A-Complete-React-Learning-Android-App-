package com.example.data.repository

import com.example.data.model.CheatSheet
import com.example.data.model.GlossaryItem
import com.example.data.model.HookDoc
import com.example.data.model.InterviewQuestion

object ReferenceData {

    val allHooks: List<HookDoc> = listOf(
        HookDoc(
            name = "useState",
            signature = "const [state, setState] = useState(initialState);",
            summary = "Declares a state variable that persists across re-renders and provides an updater function.",
            whenToUse = "Whenever a component needs to remember data that changes over time (input text, toggle status, count, fetched records).",
            syntaxExample = """const [count, setCount] = useState(0);
// Update directly:
setCount(5);
// Functional update (prev state):
setCount(prev => prev + 1);""",
            commonMistakes = listOf(
                "Mutating state directly (e.g. `state.push(item)`) instead of returning a new object or array",
                "Calling setState synchronously multiple times and assuming the value updates immediately in the next line",
                "Passing `initialState` as an expensive function call directly instead of lazy initialization `useState(() => expensive())`"
            ),
            bestPractices = listOf(
                "Group related state variables into an object or useReducer if they always update together",
                "Use the updater callback `setCount(c => c + 1)` when next state depends on current state"
            )
        ),
        HookDoc(
            name = "useEffect",
            signature = "useEffect(() => { ... return () => cleanup(); }, [dependencies]);",
            summary = "Synchronizes a component with an external system (DOM manipulation, data fetching, timers, WebSockets).",
            whenToUse = "For side effects that cannot occur directly in rendering, such as fetching data from an API, subscribing to events, or setting timers.",
            syntaxExample = """useEffect(() => {
  const handler = () => console.log('Resized');
  window.addEventListener('resize', handler);
  return () => window.removeEventListener('resize', handler);
}, []);""",
            commonMistakes = listOf(
                "Omitting the dependency array, causing the effect to run on every single render and potentially infinite loop",
                "Forgetting the cleanup function for event listeners or interval timers, causing memory leaks",
                "Using useEffect just to calculate derived state that could be calculated directly during render"
            ),
            bestPractices = listOf(
                "Keep effects focused on a single synchronization task rather than bundling all app logic into one",
                "Always list all reactive values (props, state) used inside the effect in the dependency array"
            )
        ),
        HookDoc(
            name = "useContext",
            signature = "const value = useContext(MyContext);",
            summary = "Reads and subscribes to the context value provided by the nearest <MyContext.Provider> above in the component tree.",
            whenToUse = "When data needs to be accessible by many components at different nesting levels (theme, current user, language, global UI state).",
            syntaxExample = """const ThemeContext = createContext('dark');

function Header() {
  const theme = useContext(ThemeContext);
  return <div className={`header-${'$'}{theme}`}>Header</div>;
}""",
            commonMistakes = listOf(
                "Overusing context for frequently changing state, which can cause excessive re-renders across all consumers",
                "Calling useContext outside of a matching Provider without setting a fallback default value in createContext()"
            ),
            bestPractices = listOf(
                "Create custom consumer hooks like `useTheme()` or `useAuth()` that validate the context is not undefined",
                "Split contexts (e.g. `UserContext` and `ThemeContext`) so components only re-render when the specific data they consume changes"
            )
        ),
        HookDoc(
            name = "useReducer",
            signature = "const [state, dispatch] = useReducer(reducer, initialArg, init?);",
            summary = "Alternative to useState for managing complex state transitions via action objects and pure reducer functions.",
            whenToUse = "When state transitions involve multiple sub-values, complex conditional logic, or when the next state depends heavily on previous state.",
            syntaxExample = """function reducer(state, action) {
  switch (action.type) {
    case 'increment': return { count: state.count + 1 };
    default: return state;
  }
}
const [state, dispatch] = useReducer(reducer, { count: 0 });""",
            commonMistakes = listOf(
                "Mutating the state argument inside the reducer rather than returning a new state object",
                "Performing asynchronous side effects (like API fetch) inside the reducer function (reducers must be pure)"
            ),
            bestPractices = listOf(
                "Use descriptive action types (e.g. `shoppingCart/itemAdded`)",
                "Combine useReducer with useContext for scalable lightweight global state management"
            )
        ),
        HookDoc(
            name = "useRef",
            signature = "const ref = useRef(initialValue);",
            summary = "Returns a mutable ref object `{ current: initialValue }` that persists for the entire component lifetime without triggering re-renders when mutated.",
            whenToUse = "To hold references to real DOM elements (focus, scroll), or to store mutable values (timers, previous state) that shouldn't cause a re-render.",
            syntaxExample = """const inputRef = useRef(null);
const timerRef = useRef(null);

function focusInput() {
  inputRef.current?.focus();
}""",
            commonMistakes = listOf(
                "Reading or writing `ref.current` during rendering instead of inside event handlers or useEffect",
                "Using useRef when a value directly affects what is rendered on screen (use useState instead)"
            ),
            bestPractices = listOf(
                "Use useRef to track whether a component has completed its initial mount",
                "Always check for null before accessing DOM node methods on `ref.current`"
            )
        ),
        HookDoc(
            name = "useMemo",
            signature = "const cachedValue = useMemo(() => computeExpensiveValue(a, b), [a, b]);",
            summary = "Caches the result of an expensive calculation between renders until specified dependencies change.",
            whenToUse = "When performing computationally heavy data filtering, sorting, or transformations on large arrays that would lag the UI if re-run on every render.",
            syntaxExample = """const visibleTodos = useMemo(() => {
  return filterTodos(todos, tab);
}, [todos, tab]);""",
            commonMistakes = listOf(
                "Wrapping every single variable in useMemo without measuring performance, which adds overhead with no tangible benefit",
                "Forgetting dependencies in the array, leading to stale calculation results"
            ),
            bestPractices = listOf(
                "Only optimize when you have verified that a calculation is noticeably slow (e.g. >1ms)",
                "Use useMemo to preserve referential equality for objects passed as props to React.memo children"
            )
        ),
        HookDoc(
            name = "useCallback",
            signature = "const cachedFn = useCallback(fn, [dependencies]);",
            summary = "Caches a function definition between renders until its dependencies change.",
            whenToUse = "When passing callback functions to optimized child components that rely on reference equality to prevent re-renders (wrapped in React.memo).",
            syntaxExample = """const handleClick = useCallback(() => {
  sendAnalytics(productId);
}, [productId]);""",
            commonMistakes = listOf(
                "Using useCallback on functions passed only to standard HTML elements (`<button onClick={...}>`), which provides no performance boost",
                "Missing dependencies that lead to stale closures where the function accesses old state values"
            ),
            bestPractices = listOf(
                "Pair useCallback with child components wrapped in React.memo for meaningful render prevention",
                "Use functional updates inside callbacks so you don't have to include state in the dependency array"
            )
        ),
        HookDoc(
            name = "useId",
            signature = "const id = useId();",
            summary = "Generates unique accessibility IDs that are consistent across client and server rendering to prevent hydration mismatches.",
            whenToUse = "Linking form labels to inputs with `htmlFor` and `id`, or linking ARIA description elements.",
            syntaxExample = """const id = useId();
return (
  <div>
    <label htmlFor={id}>Username:</label>
    <input id={id} type="text" />
  </div>
);""",
            commonMistakes = listOf(
                "Using useId to generate keys for mapping lists (use data IDs instead of useId for list keys)",
                "Hardcoding IDs that collide when multiple instances of a component exist on one page"
            ),
            bestPractices = listOf(
                "Use a single useId per component and append suffixes: `${'$'}{id}-first-name`, `${'$'}{id}-last-name`"
            )
        ),
        HookDoc(
            name = "useTransition",
            signature = "const [isPending, startTransition] = useTransition();",
            summary = "Allows marking state updates as non-blocking transitions so urgent updates (typing) interrupt background renders.",
            whenToUse = "For heavy re-renders (e.g. searching 10,000 items or tab switching) where you want to keep the text input responsive.",
            syntaxExample = """const [isPending, startTransition] = useTransition();

function handleChange(e) {
  setQuery(e.target.value); // Urgent: update text box immediately
  startTransition(() => {
    setFilter(e.target.value); // Non-urgent: large list re-filter
  });
}""",
            commonMistakes = listOf(
                "Wrapping controlled input values in startTransition (typing must remain urgent)",
                "Using useTransition for simple states where re-renders are already fast"
            ),
            bestPractices = listOf(
                "Use `isPending` to show subtle loading indicators or dim background panels while transitions calculate"
            )
        )
    )

    val cheatSheets: List<CheatSheet> = listOf(
        CheatSheet(
            category = "JSX & Components",
            title = "JSX & Components Quick Reference",
            description = "Essential syntax rules and patterns for writing clean React components",
            snippets = listOf(
                "Component Declaration" to "function Welcome({ name = 'Guest' }) {\n  return <h1>Hello, {name}!</h1>;\n}",
                "Conditional Rendering" to "{isLoggedIn ? <Dashboard /> : <LoginForm />}\n{hasUnread && <Badge count={unreadCount} />}",
                "List Rendering with Keys" to "<ul>\n  {items.map(item => (\n    <li key={item.id}>{item.title}</li>\n  ))}\n</ul>",
                "Fragments" to "<>\n  <Header />\n  <MainContent />\n</>"
            )
        ),
        CheatSheet(
            category = "Hooks",
            title = "React Hooks Cheatsheet",
            description = "The most commonly used hook patterns and syntaxes",
            snippets = listOf(
                "useState Basic & Functional" to "const [count, setCount] = useState(0);\nsetCount(prev => prev + 1);",
                "useEffect with Cleanup" to "useEffect(() => {\n  const sub = api.subscribe();\n  return () => sub.unsubscribe();\n}, [id]);",
                "useRef for DOM Access" to "const inputRef = useRef(null);\ninputRef.current?.focus();",
                "Custom Hook Pattern" to "function useOnline() {\n  const [online, setOnline] = useState(navigator.onLine);\n  // listeners...\n  return online;\n}"
            )
        ),
        CheatSheet(
            category = "Performance",
            title = "React Performance & Optimization",
            description = "Memoization, render prevention, and profiling rules",
            snippets = listOf(
                "React.memo Component" to "const UserCard = React.memo(function UserCard({ user }) {\n  return <div>{user.name}</div>;\n});",
                "useCallback Handler" to "const onDelete = useCallback((id) => {\n  setItems(prev => prev.filter(i => i.id !== id));\n}, []);",
                "useMemo Calculation" to "const sorted = useMemo(() => {\n  return [...items].sort((a,b) => b.score - a.score);\n}, [items]);"
            )
        )
    )

    val glossary: List<GlossaryItem> = listOf(
        GlossaryItem("Component", "Core", "A self-contained, reusable block of UI that accepts props and returns JSX.", "function Card() { return <div>Card</div>; }"),
        GlossaryItem("Props", "Core", "Read-only arguments passed from parent components to child components to configure them.", "<UserCard name=\"Awiskar\" role=\"Lead\" />"),
        GlossaryItem("State", "Core", "Data managed internally by a component that can change over time and triggers re-renders when updated.", "const [count, setCount] = useState(0);"),
        GlossaryItem("Virtual DOM", "Engine", "An in-memory lightweight representation of the real DOM tree used by React to compute efficient updates.", "React creates virtual elements before patching the browser."),
        GlossaryItem("Reconciliation", "Engine", "The algorithm React uses to diff one tree of elements with another to determine which parts need to be changed in the real DOM.", "Diffing algorithm with heuristic O(n) comparison."),
        GlossaryItem("Fiber", "Engine", "The internal reimplementation of React's core reconciliation algorithm that enables concurrent, interruptible rendering.", "React 16+ engine structure."),
        GlossaryItem("Prop Drilling", "Pattern", "The process of passing props through several intermediate components that don't need the data themselves just to reach a deeply nested child.", "Solved using React Context or global stores."),
        GlossaryItem("Hydration", "SSR", "The process where client-side React attaches event listeners and state to server-rendered HTML markup.", "Used in Next.js and Remix fullstack frameworks.")
    )

    val interviewQuestions: List<InterviewQuestion> = listOf(
        InterviewQuestion(
            id = "iq_1",
            question = "What is the Virtual DOM and how does reconciliation work?",
            category = "Fundamentals",
            difficulty = "Junior",
            answerSummary = "The Virtual DOM is an in-memory representation of real DOM elements. When state changes, React generates a new Virtual DOM tree and diffs it with the previous snapshot (Reconciliation) to calculate minimal real DOM mutations.",
            detailedAnswer = "Direct DOM manipulation is slow because it causes browser layout recalculations and repaints. React maintains two virtual trees in memory: current and previous. During reconciliation, React applies heuristic O(n) algorithms: elements with different types produce different trees, and lists with unique keys are efficiently matched across renders.",
            options = listOf(
                "An in-memory tree snapshot used to compute minimal real DOM diffs",
                "A plugin that runs React inside Web Workers",
                "A backend database cache",
                "An encrypted storage system in the browser"
            ),
            correctOptionIndex = 0
        ),
        InterviewQuestion(
            id = "iq_2",
            question = "What is the difference between controlled and uncontrolled components?",
            category = "Fundamentals",
            difficulty = "Junior",
            answerSummary = "In controlled components, form data is handled by React state through value and onChange. In uncontrolled components, form data is handled by the browser DOM itself and accessed using refs.",
            detailedAnswer = "Controlled components provide a single source of truth: React manages the input state. This makes validation, masking, and dynamic enabling trivial. Uncontrolled components rely on the DOM's native state (`defaultValue`) and values are pulled on demand using `useRef`.",
            options = listOf(
                "Controlled components have state driven by React; uncontrolled derive values from the DOM directly",
                "Controlled components require Redux; uncontrolled do not",
                "Controlled components run on the server; uncontrolled run on client",
                "Controlled components cannot have buttons"
            ),
            correctOptionIndex = 0
        ),
        InterviewQuestion(
            id = "iq_3",
            question = "Explain the rules of useEffect dependencies and what causes infinite loops.",
            category = "Hooks & State",
            difficulty = "Mid",
            answerSummary = "useEffect re-runs whenever any value in its dependency array changes (checked via Object.is). An infinite loop occurs if useEffect updates a state variable that is also included in the dependency array without a break condition, or if dependencies are missing.",
            detailedAnswer = "If you call setState inside an effect without dependencies `useEffect(() => { setState(x) })`, it executes after render, sets state, schedules another render, which runs the effect again indefinitely. Always list all props and state read inside the effect in the dependency array or use functional state updates.",
            options = listOf(
                "Updating state inside an effect without dependencies triggers continuous re-renders",
                "useEffect only runs when an error is thrown",
                "Dependencies must always be empty arrays",
                "Browser cookies force effects to re-run"
            ),
            correctOptionIndex = 0
        ),
        InterviewQuestion(
            id = "iq_4",
            question = "When should you use useMemo and useCallback, and what are their trade-offs?",
            category = "Architecture & Performance",
            difficulty = "Senior",
            answerSummary = "useMemo caches calculation results; useCallback caches function definitions. Use them when passing callbacks to React.memo children or when performing expensive calculations. Overusing them adds memory overhead and unnecessary comparison costs.",
            detailedAnswer = "Every hook invocation incurs memory allocation for dependency arrays and comparison checks on every render. If the computation is trivial (e.g. adding two numbers) or the child component isn't memoized with React.memo, useCallback and useMemo offer zero performance benefits and make code harder to read.",
            options = listOf(
                "When passing callbacks to React.memo children or caching computationally heavy operations",
                "On every single function and variable in the app",
                "Only when connecting to WebSocket servers",
                "Only when rendering HTML Canvas elements"
            ),
            correctOptionIndex = 0
        )
    )
}
