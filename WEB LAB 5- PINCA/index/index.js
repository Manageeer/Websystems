// index.js — CRUD UI using Fetch API
const backendBase = 'http://localhost:8080';
const username = 'darshan';
const url = `${backendBase}/users/${encodeURIComponent(username)}/todos`;

const todoTbody = document.getElementById('todo-tbody');
const form = document.getElementById('todo-form');
const descInput = document.getElementById('todo-description');
const dateInput = document.getElementById('todo-targetDate');
const statusEl = document.getElementById('status');

const todosById = new Map();

function setStatus(msg, isError = false) {
  statusEl.textContent = msg || '';
  statusEl.style.color = isError ? '#dc2626' : '#6b7280';
}

// === READ ===
async function fetchTodos() {
  setStatus('Loading todos...');
  try {
    const response = await fetch(url);
    if (!response.ok) throw new Error(`Failed to fetch todos (${response.status})`);
    const todos = await response.json();
    renderTodos(todos);
    setStatus(`Loaded ${todos.length} todos.`);
  } catch (error) {
    console.error('Error fetching todos:', error);
    setStatus('Cannot reach backend. Showing local sample data.', true);
    const sample = [
      { id: 10001, username, description: 'Learn Java Spring Framework', targetDate: '2025-10-08', done: false },
      { id: 10002, username, description: 'Learn Fullstack Development', targetDate: '2025-10-08', done: false },
      { id: 10003, username, description: 'Learn React and Next Js', targetDate: '2025-10-08', done: false }
    ];
    renderTodos(sample);
  }
}

// === CREATE ===
form.addEventListener('submit', async (e) => {
  e.preventDefault();
  const description = descInput.value.trim();
  const targetDate = dateInput.value || new Date().toISOString().slice(0,10);

  if (!description) {
    alert('Please enter a description.');
    return;
  }

  const newTodo = {
    description,
    targetDate,
    username,
    done: false
  };

  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newTodo)
    });
    if (!response.ok) throw new Error(`Failed to add todo (${response.status})`);
    const created = await response.json();
    addRowToTable(created);
    descInput.value = '';
    dateInput.value = '';
    setStatus('Todo added.');
  } catch (error) {
    console.error('Error adding todo:', error);
    const fakeId = Math.floor(Math.random() * 100000) + 20000;
    const created = { id: fakeId, ...newTodo };
    addRowToTable(created);
    setStatus('Added locally (backend unreachable).', true);
  }
});

// === UPDATE ===
async function updateTodo(id, updatedTodo) {
  try {
    const response = await fetch(`${url}/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(updatedTodo)
    });
    if (!response.ok) throw new Error(`Failed to update todo (${response.status})`);
    const returned = await response.json();
    todosById.set(id, returned);
    refreshRowFromTodo(id, returned);
    setStatus('Todo updated.');
    return returned;
  } catch (error) {
    console.error('Error updating todo:', error);
    setStatus('Failed to update on server. Change saved locally only.', true);
    todosById.set(id, updatedTodo);
    refreshRowFromTodo(id, updatedTodo);
    return updatedTodo;
  }
}

// === DELETE ===
async function deleteTodo(id) {
  if (!confirm('Delete this todo?')) return;
  try {
    const response = await fetch(`${url}/${id}`, { method: 'DELETE' });
    if (!response.ok) throw new Error(`Failed to delete todo (${response.status})`);
    todosById.delete(id);
    const row = document.getElementById(`todo-${id}`);
    if (row) row.remove();
    setStatus('Todo deleted.');
  } catch (error) {
    console.error('Error deleting todo:', error);
    todosById.delete(id);
    const row = document.getElementById(`todo-${id}`);
    if (row) row.remove();
    setStatus('Deleted locally (backend unreachable).', true);
  }
}

// === RENDER helpers ===
function renderTodos(todos) {
  todoTbody.innerHTML = '';
  todosById.clear();
  todos.forEach(todo => {
    if (typeof todo.id === 'undefined' || todo.id === null) {
      todo.id = Math.floor(Math.random() * 100000) + 1000;
    }
    todosById.set(todo.id, todo);
    addRowToTable(todo);
  });
}

function refreshRowFromTodo(id, todo) {
  const row = document.getElementById(`todo-${id}`);
  if (!row) return;
  row.querySelector('td:nth-child(2)').textContent = todo.description || '';
  row.querySelector('td:nth-child(3)').textContent = todo.targetDate || '';
  const cb = row.querySelector('input[type="checkbox"]');
  if (cb) cb.checked = !!todo.done;
}

function addRowToTable(todo) {
  if (document.getElementById(`todo-${todo.id}`)) return;

  const tr = document.createElement('tr');
  tr.id = `todo-${todo.id}`;

  const tdId = document.createElement('td');
  tdId.textContent = todo.id;

  const tdDesc = document.createElement('td');
  tdDesc.textContent = todo.description || '';

  const tdDate = document.createElement('td');
  tdDate.textContent = todo.targetDate || '';

  const tdDone = document.createElement('td');
  const doneCheckbox = document.createElement('input');
  doneCheckbox.type = 'checkbox';
  doneCheckbox.checked = !!todo.done;
  doneCheckbox.addEventListener('change', () => {
    const current = todosById.get(todo.id) || {};
    const payload = {
      description: current.description || todo.description || '',
      targetDate: current.targetDate || todo.targetDate || new Date().toISOString().slice(0,10),
      username: current.username || username,
      done: doneCheckbox.checked
    };
    updateTodo(todo.id, payload);
  });
  tdDone.appendChild(doneCheckbox);

  const tdActions = document.createElement('td');
  const editBtn = document.createElement('button');
  editBtn.textContent = 'Edit';
  editBtn.className = 'edit';
  editBtn.addEventListener('click', async () => {
    const current = todosById.get(todo.id) || todo;
    const newDesc = prompt('New description:', current.description || '');
    if (newDesc == null) return;
    const newDate = prompt('New target date (YYYY-MM-DD):', current.targetDate || new Date().toISOString().slice(0,10));
    if (newDate == null) return;
    const payload = {
      description: newDesc,
      targetDate: newDate,
      username: current.username || username,
      done: !!current.done
    };
    await updateTodo(todo.id, payload);
  });

  const deleteBtn = document.createElement('button');
  deleteBtn.textContent = 'Delete';
  deleteBtn.className = 'delete';
  deleteBtn.addEventListener('click', () => deleteTodo(todo.id));

  tdActions.append(editBtn, deleteBtn);
  tr.append(tdId, tdDesc, tdDate, tdDone, tdActions);
  todoTbody.appendChild(tr);
  todosById.set(todo.id, todo);
}

// Initialize
fetchTodos();