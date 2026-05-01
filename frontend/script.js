// Configuration
const API_BASE_URL = 'http://localhost:8080/api';

// State
let vehicles = [];
let services = [];
let currentEditingServiceId = null;

// Initialize app
document.addEventListener('DOMContentLoaded', () => {
    loadVehicles();
    loadServices();
    setupEventListeners();
});

// Page Navigation
function showPage(pageId) {
    // Hide all pages
    document.querySelectorAll('.page').forEach(page => {
        page.style.display = 'none';
    });
    
    // Show selected page
    document.getElementById(pageId).style.display = 'block';
    
    // Load data when navigating to different pages
    if (pageId === 'log-service') {
        loadVehicles(); // Refresh vehicles when going to log service
    } else if (pageId === 'history') {
        populateHistoryFilters();
        displayServices(services);
    } else if (pageId === 'analytics') {
        updateAnalytics();
    }
}

// Setup Event Listeners
function setupEventListeners() {
    // Listen for vehicle deletion from vehicle cards
    document.addEventListener('vehicleDeleted', loadVehicles);
    document.addEventListener('serviceLogged', () => {
        loadServices();
        showSuccessMessage('Service logged successfully!');
    });
    document.addEventListener('serviceUpdated', () => {
        loadServices();
        showSuccessMessage('Service updated successfully!');
    });
}

// =====================
// Vehicle Management
// =====================

function showVehicleForm() {
    document.getElementById('vehicle-form').style.display = 'block';
    document.querySelector('#vehicle-form form').reset();
}

function hideVehicleForm() {
    document.getElementById('vehicle-form').style.display = 'none';
}

async function handleAddVehicle(event) {
    event.preventDefault();

    const vehicle = {
        name: document.getElementById('vehicle-name').value,
        number: document.getElementById('vehicle-number').value,
        make: document.getElementById('vehicle-make').value,
        model: document.getElementById('vehicle-model').value,
        year: parseInt(document.getElementById('vehicle-year').value),
        type: document.getElementById('vehicle-type').value
    };

    try {
        const response = await fetch(`${API_BASE_URL}/vehicles`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(vehicle)
        });

        if (response.ok) {
            showSuccessMessage('Vehicle added successfully!');
            hideVehicleForm();
            loadVehicles();
        } else {
            showErrorMessage('Failed to add vehicle');
        }
    } catch (error) {
        console.error('Error:', error);
        showErrorMessage('Error adding vehicle');
    }
}

async function loadVehicles() {
    try {
        const response = await fetch(`${API_BASE_URL}/vehicles`);
        vehicles = await response.json();
        displayVehicles();
        updateVehicleSelects();
    } catch (error) {
        console.error('Error loading vehicles:', error);
        showErrorMessage('Error loading vehicles');
    }
}

function displayVehicles() {
    const container = document.getElementById('vehicles-list');

    if (vehicles.length === 0) {
        container.innerHTML = '<div class="empty-state"><p>No vehicles added yet. Click "Add New Vehicle" to get started!</p></div>';
        return;
    }

    container.innerHTML = vehicles.map(vehicle => `
        <div class="vehicle-card">
            <h3>${vehicle.name}</h3>
            <p><span class="vehicle-label">Number:</span> ${vehicle.number}</p>
            <p><span class="vehicle-label">Make:</span> ${vehicle.make}</p>
            <p><span class="vehicle-label">Model:</span> ${vehicle.model}</p>
            <p><span class="vehicle-label">Year:</span> ${vehicle.year}</p>
            <span class="vehicle-type ${vehicle.type.toLowerCase()}">${vehicle.type}</span>
            <div class="vehicle-actions">
                <button class="btn btn-edit" onclick="editVehicle('${vehicle.id}')">Edit</button>
                <button class="btn btn-danger" onclick="deleteVehicle('${vehicle.id}')">Delete</button>
            </div>
        </div>
    `).join('');
}

function updateVehicleSelects() {
    const selects = ['service-vehicle', 'filter-vehicle', 'edit-service-vehicle'];
    
    selects.forEach(selectId => {
        const select = document.getElementById(selectId);
        const currentValue = select.value;
        
        select.innerHTML = '<option value="">Select Vehicle</option>';
        vehicles.forEach(vehicle => {
            const option = document.createElement('option');
            option.value = vehicle.id;
            option.text = `${vehicle.name} (${vehicle.number})`;
            select.appendChild(option);
        });
        
        select.value = currentValue;
    });
}

async function deleteVehicle(vehicleId) {
    if (!confirm('Are you sure you want to delete this vehicle?')) return;

    try {
        const response = await fetch(`${API_BASE_URL}/vehicles/${vehicleId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showSuccessMessage('Vehicle deleted successfully!');
            loadVehicles();
            // Also remove associated services
            loadServices();
        } else {
            showErrorMessage('Failed to delete vehicle');
        }
    } catch (error) {
        console.error('Error:', error);
        showErrorMessage('Error deleting vehicle');
    }
}

function editVehicle(vehicleId) {
    const vehicle = vehicles.find(v => v.id === vehicleId);
    if (vehicle) {
        document.getElementById('vehicle-name').value = vehicle.name;
        document.getElementById('vehicle-make').value = vehicle.make;
        document.getElementById('vehicle-model').value = vehicle.model;
        document.getElementById('vehicle-year').value = vehicle.year;
        document.getElementById('vehicle-type').value = vehicle.type;
        
        showVehicleForm();
        // TODO: Update form to handle edit mode
    }
}

// =====================
// Service Management
// =====================

async function handleLogService(event) {
    event.preventDefault();

    const vehicleId = document.getElementById('service-vehicle').value;
    const vehicleName = vehicles.find(v => v.id === vehicleId)?.name || '';

    const service = {
        vehicleId: vehicleId,
        vehicleName: vehicleName,
        serviceType: document.getElementById('service-type').value,
        serviceDate: document.getElementById('service-date').value,
        cost: parseFloat(document.getElementById('service-cost').value),
        mileage: parseInt(document.getElementById('service-mileage').value),
        mechanic: document.getElementById('service-mechanic').value,
        notes: document.getElementById('service-notes').value
    };

    try {
        const response = await fetch(`${API_BASE_URL}/services`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(service)
        });

        if (response.ok) {
            showSuccessMessage('Service logged successfully!');
            document.querySelector('#log-service form').reset();
            loadServices();
        } else {
            showErrorMessage('Failed to log service');
        }
    } catch (error) {
        console.error('Error:', error);
        showErrorMessage('Error logging service');
    }
}

async function loadServices() {
    try {
        const response = await fetch(`${API_BASE_URL}/services`);
        services = await response.json();
        displayServices(services);
    } catch (error) {
        console.error('Error loading services:', error);
        showErrorMessage('Error loading services');
    }
}

function displayServices(servicesToDisplay = services) {
    const container = document.getElementById('services-table');

    if (servicesToDisplay.length === 0) {
        container.innerHTML = '<div class="empty-state"><p>No services logged yet. Start logging services from the "Log Service" page!</p></div>';
        return;
    }

    const tableHTML = `
        <table>
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Vehicle</th>
                    <th>Service Type</th>
                    <th>Cost</th>
                    <th>Mileage</th>
                    <th>Mechanic</th>
                    <th>Notes</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                ${servicesToDisplay.map(service => `
                    <tr>
                        <td>${formatDate(service.serviceDate)}</td>
                        <td>${service.vehicleName}</td>
                        <td>${service.serviceType}</td>
                        <td>$${service.cost.toFixed(2)}</td>
                        <td>${service.mileage} mi</td>
                        <td>${service.mechanic || '-'}</td>
                        <td>${truncateText(service.notes || '', 30)}</td>
                        <td>
                            <div class="table-actions">
                                <button class="btn btn-secondary" onclick="showServiceDetails('${service.id}')">View</button>
                                <button class="btn btn-edit" onclick="openEditModal('${service.id}')">Edit</button>
                                <button class="btn btn-danger" onclick="deleteService('${service.id}')">Delete</button>
                            </div>
                        </td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
    `;

    container.innerHTML = tableHTML;
}

function populateHistoryFilters() {
    const vehicleSelect = document.getElementById('filter-vehicle');
    vehicleSelect.innerHTML = '<option value="">All Vehicles</option>';
    vehicles.forEach(vehicle => {
        const option = document.createElement('option');
        option.value = vehicle.id;
        option.text = vehicle.name;
        vehicleSelect.appendChild(option);
    });

    const serviceTypeSelect = document.getElementById('filter-service-type');
    const uniqueTypes = [...new Set(services.map(s => s.serviceType))];
    serviceTypeSelect.innerHTML = '<option value="">All Service Types</option>';
    uniqueTypes.forEach(type => {
        const option = document.createElement('option');
        option.value = type;
        option.text = type;
        serviceTypeSelect.appendChild(option);
    });
}

function applyFilters() {
    const vehicleFilter = document.getElementById('filter-vehicle').value;
    const serviceTypeFilter = document.getElementById('filter-service-type').value;

    let filtered = services;

    if (vehicleFilter) {
        filtered = filtered.filter(s => s.vehicleId === vehicleFilter);
    }

    if (serviceTypeFilter) {
        filtered = filtered.filter(s => s.serviceType === serviceTypeFilter);
    }

    displayServices(filtered);
}

async function deleteService(serviceId) {
    if (!confirm('Are you sure you want to delete this service record?')) return;

    try {
        const response = await fetch(`${API_BASE_URL}/services/${serviceId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showSuccessMessage('Service deleted successfully!');
            loadServices();
        } else {
            showErrorMessage('Failed to delete service');
        }
    } catch (error) {
        console.error('Error:', error);
        showErrorMessage('Error deleting service');
    }
}

function showServiceDetails(serviceId) {
    const service = services.find(s => s.id === serviceId);
    if (!service) {
        showErrorMessage('Service not found');
        return;
    }
    const vehicle = vehicles.find(v => v.id === service.vehicleId);
    const otherServices = services
        .filter(s => s.vehicleId === service.vehicleId && s.id !== service.id)
        .sort((a, b) => new Date(b.serviceDate) - new Date(a.serviceDate));

    const vehicleHTML = vehicle ? `
        <div class="vehicle-card">
            <h3>${vehicle.name}</h3>
            <p><span class="vehicle-label">Number:</span> ${vehicle.number}</p>
            <p><span class="vehicle-label">Make:</span> ${vehicle.make}</p>
            <p><span class="vehicle-label">Model:</span> ${vehicle.model}</p>
            <p><span class="vehicle-label">Year:</span> ${vehicle.year}</p>
            <span class="vehicle-type ${vehicle.type.toLowerCase()}">${vehicle.type}</span>
        </div>
    ` : `<div class="vehicle-card"><p>Vehicle no longer exists (was: ${service.vehicleName || 'Unknown'})</p></div>`;

    const otherHTML = otherServices.length === 0
        ? '<p style="color:#999;">No other services for this vehicle.</p>'
        : `<table>
            <thead><tr><th>Date</th><th>Service Type</th><th>Cost</th><th>Mileage</th></tr></thead>
            <tbody>${otherServices.map(s => `
                <tr style="cursor:pointer;" onclick="showServiceDetails('${s.id}')">
                    <td>${formatDate(s.serviceDate)}</td>
                    <td>${s.serviceType}</td>
                    <td>$${s.cost.toFixed(2)}</td>
                    <td>${s.mileage} mi</td>
                </tr>`).join('')}
            </tbody>
        </table>`;

    document.getElementById('service-details-content').innerHTML = `
        <div class="form-container">
            <h3>Vehicle</h3>
            ${vehicleHTML}
        </div>
        <div class="form-container">
            <h3>Service Record</h3>
            <p><strong>Date:</strong> ${formatDate(service.serviceDate)}</p>
            <p><strong>Service Type:</strong> ${service.serviceType}</p>
            <p><strong>Cost:</strong> $${service.cost.toFixed(2)}</p>
            <p><strong>Mileage:</strong> ${service.mileage} mi</p>
            <p><strong>Mechanic/Shop:</strong> ${service.mechanic || '-'}</p>
            <p><strong>Notes:</strong> ${service.notes || '-'}</p>
            <div class="table-actions" style="margin-top:15px;">
                <button class="btn btn-edit" onclick="openEditModal('${service.id}')">Edit</button>
                <button class="btn btn-danger" onclick="deleteService('${service.id}'); showPage('history');">Delete</button>
            </div>
        </div>
        <div class="form-container">
            <h3>Other Services for This Vehicle</h3>
            ${otherHTML}
        </div>
    `;

    showPage('service-details');
}

async function openEditModal(serviceId) {
    const service = services.find(s => s.id === serviceId);
    if (!service) return;

    currentEditingServiceId = serviceId;
    document.getElementById('edit-service-id').value = service.id;
    document.getElementById('edit-service-vehicle').value = service.vehicleId;
    document.getElementById('edit-service-type').value = service.serviceType;
    document.getElementById('edit-service-date').value = service.serviceDate;
    document.getElementById('edit-service-cost').value = service.cost;
    document.getElementById('edit-service-mileage').value = service.mileage;
    document.getElementById('edit-service-mechanic').value = service.mechanic;
    document.getElementById('edit-service-notes').value = service.notes;

    document.getElementById('edit-modal').style.display = 'block';
}

function closeEditModal() {
    document.getElementById('edit-modal').style.display = 'none';
    currentEditingServiceId = null;
}

async function handleUpdateService(event) {
    event.preventDefault();

    const serviceId = document.getElementById('edit-service-id').value;
    const vehicleId = document.getElementById('edit-service-vehicle').value;
    const vehicleName = vehicles.find(v => v.id === vehicleId)?.name || '';

    const service = {
        vehicleId: vehicleId,
        vehicleName: vehicleName,
        serviceType: document.getElementById('edit-service-type').value,
        serviceDate: document.getElementById('edit-service-date').value,
        cost: parseFloat(document.getElementById('edit-service-cost').value),
        mileage: parseInt(document.getElementById('edit-service-mileage').value),
        mechanic: document.getElementById('edit-service-mechanic').value,
        notes: document.getElementById('edit-service-notes').value
    };

    try {
        const response = await fetch(`${API_BASE_URL}/services/${serviceId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(service)
        });

        if (response.ok) {
            showSuccessMessage('Service updated successfully!');
            closeEditModal();
            loadServices();
        } else {
            showErrorMessage('Failed to update service');
        }
    } catch (error) {
        console.error('Error:', error);
        showErrorMessage('Error updating service');
    }
}

// =====================
// Analytics
// =====================

async function updateAnalytics() {
    // Total vehicles
    document.getElementById('analytics-total-vehicles').textContent = vehicles.length;

    // Total services
    document.getElementById('analytics-total-services').textContent = services.length;

    // Total spent
    const totalSpent = services.reduce((sum, service) => sum + service.cost, 0);
    document.getElementById('analytics-total-spent').textContent = `$${totalSpent.toFixed(2)}`;

    // Vehicle-specific analytics
    const vehicleAnalyticsContainer = document.getElementById('vehicle-analytics');
    if (vehicles.length === 0) {
        vehicleAnalyticsContainer.innerHTML = '<p style="text-align: center; color: #999;">No vehicles to analyze</p>';
        return;
    }

    vehicleAnalyticsContainer.innerHTML = vehicles.map(vehicle => {
        const vehicleServices = services.filter(s => s.vehicleId === vehicle.id);
        const vehicleTotalCost = vehicleServices.reduce((sum, service) => sum + service.cost, 0);
        const lastService = vehicleServices.length > 0 ? vehicleServices[0] : null;

        return `
            <div class="vehicle-analytics-item">
                <h4>${vehicle.name}</h4>
                <p><strong>Total Services:</strong> ${vehicleServices.length}</p>
                <p><strong>Total Spent:</strong> $${vehicleTotalCost.toFixed(2)}</p>
                <p><strong>Last Service:</strong> ${lastService ? formatDate(lastService.serviceDate) : 'None'}</p>
                <p><strong>Type:</strong> ${vehicle.type}</p>
                <div class="reminder-block" data-vehicle-id="${vehicle.id}">
                    <p><strong>Service Reminder</strong></p>
                    <label>Current Mileage:
                        <input type="number" min="0" class="reminder-mileage" placeholder="enter current mileage"
                               oninput="loadReminder('${vehicle.id}')">
                    </label>
                    <div class="reminder-output" style="margin-top:8px; color:#555;">
                        <em>Loading…</em>
                    </div>
                </div>
            </div>
        `;
    }).join('');

    vehicles.forEach(v => loadReminder(v.id));
}

async function loadReminder(vehicleId) {
    const block = document.querySelector(`.reminder-block[data-vehicle-id="${vehicleId}"]`);
    if (!block) return;
    const input = block.querySelector('.reminder-mileage');
    const output = block.querySelector('.reminder-output');

    let url = `${API_BASE_URL}/reminders/${vehicleId}`;
    if (input.value !== '') {
        url += `?currentMileage=${encodeURIComponent(input.value)}`;
    }

    try {
        const response = await fetch(url);
        if (!response.ok) {
            output.innerHTML = '<em>Could not load reminder</em>';
            return;
        }
        const r = await response.json();
        if (input.value === '' && r.lastServiceMileage !== undefined) {
            input.placeholder = `last service: ${r.lastServiceMileage} mi`;
        }
        const status = !r.hasServiceHistory
            ? '<span style="color:#999;">No service history yet</span>'
            : r.milesUntilService === 0
                ? '<span style="color:var(--danger-color); font-weight:600;">Service due now</span>'
                : `<span>Due in <strong>${r.milesUntilService}</strong> mi (~${r.daysUntilService} days)</span>`;
        output.innerHTML = `
            ${status}
            <p style="margin-top:4px; font-size:0.9em;">
                Interval: ${r.serviceIntervalMiles} mi · Next service at ${r.nextServiceMileage} mi
            </p>
        `;
    } catch (e) {
        output.innerHTML = '<em>Error loading reminder</em>';
    }
}

// =====================
// Utility Functions
// =====================

function formatDate(dateString) {
    const options = { year: 'numeric', month: 'short', day: 'numeric' };
    return new Date(dateString).toLocaleDateString('en-US', options);
}

function truncateText(text, length) {
    return text.length > length ? text.substring(0, length) + '...' : text;
}

function showSuccessMessage(message) {
    const notification = document.createElement('div');
    notification.className = 'success-message';
    notification.textContent = message;
    document.body.insertBefore(notification, document.body.firstChild);
    
    setTimeout(() => notification.remove(), 3000);
}

function showErrorMessage(message) {
    const notification = document.createElement('div');
    notification.className = 'error-message';
    notification.textContent = message;
    document.body.insertBefore(notification, document.body.firstChild);
    
    setTimeout(() => notification.remove(), 3000);
}

// Close modal when clicking outside of it
window.onclick = function(event) {
    const modal = document.getElementById('edit-modal');
    if (event.target === modal) {
        closeEditModal();
    }
}
