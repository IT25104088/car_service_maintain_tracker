# Car Service & Maintenance Tracker

A comprehensive application for tracking vehicle maintenance and service history. Built with Spring Boot backend, vanilla HTML/CSS/JavaScript frontend, and text-file based data storage.

## 📋 Features

### Vehicle Management
- **Add Vehicles**: Register multiple vehicles with details (name, make, model, year, type)
- **Vehicle Types**: Support for Gasoline and Electric vehicles
- **Edit/Delete**: Manage your vehicle fleet

### Service Logging
- **Record Services**: Log maintenance services with comprehensive details
- **Service Types**: Oil Change, Brake Service, Tire Rotation, Battery Replacement, Coolant Flush, Air Filter, and more
- **Service Details**: Track date, cost, mileage, mechanic, and notes

### Service History
- **View History**: Browse all service records with filtering capabilities
- **Filter by Vehicle**: See services for a specific vehicle
- **Filter by Type**: Find services by type (e.g., all oil changes)
- **Edit Records**: Update service information
- **Delete Records**: Remove incorrect or duplicate entries

### Analytics
- **Dashboard Stats**: Total vehicles, total services logged, total spending
- **Per-Vehicle Analytics**: Service count, spending, and last service date for each vehicle

## 🏗️ Architecture

### Backend (Java Spring Boot)
- **Controllers**: REST API endpoints for vehicles and services
- **Services**: Business logic layer
- **Repositories**: Text file-based data persistence
- **Models**: Entity classes with OOP principles
  - **Encapsulation**: Private fields with getters/setters
  - **Abstraction**: ServiceReminder abstract class
  - **Polymorphism**: Different reminder calculations for Gasoline vs Electric vehicles

### Frontend (HTML/CSS/JavaScript)
- **Responsive Design**: Works on desktop and mobile
- **Single Page Application**: Smooth navigation between sections
- **RESTful API Integration**: Communicates with backend via HTTP
- **Real-time Filtering**: Dynamic data filtering without page reload

### Data Storage (Text Files)
- **services.txt**: Service record data (pipe-separated)
- **vehicles.txt**: Vehicle data (pipe-separated)
- **No Database Required**: Simple text file storage for easy setup and portability

## 📦 Project Structure

```
Car-Service-And-Maintenance-Tracker/
├── backend/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/cartracker/
│       │   ├── CarTrackerApplication.java
│       │   ├── controller/
│       │   │   ├── VehicleController.java
│       │   │   └── ServiceController.java
│       │   ├── service/
│       │   │   ├── VehicleService.java
│       │   │   └── ServiceService.java
│       │   ├── repository/
│       │   │   ├── FileBasedRepository.java
│       │   │   ├── VehicleRepository.java
│       │   │   └── ServiceRecordRepository.java
│       │   └── model/
│       │       ├── Vehicle.java
│       │       ├── ServiceRecord.java
│       │       └── ServiceReminder.java
│       └── resources/
│           └── application.properties
├── frontend/
│   ├── index.html
│   ├── styles.css
│   └── script.js
├── data/
│   ├── vehicles.txt
│   └── services.txt
└── README.md
```

## 🚀 Getting Started

### Prerequisites
- Java 11+
- Maven 3.6+
- Modern web browser

### Backend Setup

1. **Navigate to backend directory:**
   ```bash
   cd backend
   ```

2. **Build the project:**
   ```bash
   mvn clean package
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

   The backend will start on `http://localhost:8080`

### Frontend Setup

1. **Navigate to frontend directory:**
   ```bash
   cd frontend
   ```

2. **Open in browser:**
   - Simply open `index.html` in your web browser, or
   - Use a local server: `python -m http.server 3000` (requires Python)

3. **Access the application:**
   - Open `http://localhost:3000` (if using Python server) or open `index.html` directly

## 📖 Usage

### Adding a Vehicle
1. Click "Vehicles" tab
2. Click "+ Add New Vehicle"
3. Fill in vehicle details (name, make, model, year, type)
4. Click "Add Vehicle"

### Logging a Service
1. Click "Log Service" tab
2. Select vehicle from dropdown
3. Choose service type
4. Enter service date, cost, and current mileage
5. (Optional) Add mechanic name and notes
6. Click "Log Service"

### Viewing Service History
1. Click "Service History" tab
2. (Optional) Filter by vehicle or service type
3. View all services in table format
4. Click "Edit" to modify a record or "Delete" to remove it

### Viewing Analytics
1. Click "Analytics" tab
2. View overall statistics (total vehicles, services, spending)
3. See per-vehicle breakdown with spending and last service date

## 🔌 API Endpoints

### Vehicles
- `GET /api/vehicles` - Get all vehicles
- `GET /api/vehicles/{id}` - Get vehicle by ID
- `POST /api/vehicles` - Create new vehicle
- `PUT /api/vehicles/{id}` - Update vehicle
- `DELETE /api/vehicles/{id}` - Delete vehicle
- `GET /api/vehicles/type/{type}` - Get vehicles by type

### Services
- `GET /api/services` - Get all services
- `GET /api/services/{id}` - Get service by ID
- `POST /api/services` - Create new service
- `PUT /api/services/{id}` - Update service
- `DELETE /api/services/{id}` - Delete service
- `GET /api/services/vehicle/{vehicleId}` - Get services for a vehicle
- `GET /api/services/vehicle/{vehicleId}/type/{serviceType}` - Get services by type for a vehicle
- `GET /api/services/vehicle/{vehicleId}/total-cost` - Get total cost for a vehicle

## 📝 Example Requests

### Create a Vehicle
```json
POST /api/vehicles
{
  "name": "My Tesla",
  "make": "Tesla",
  "model": "Model 3",
  "year": 2023,
  "type": "ELECTRIC"
}
```

### Log a Service
```json
POST /api/services
{
  "vehicleId": "550e8400-e29b-41d4-a716-446655440000",
  "vehicleName": "My Tesla",
  "serviceType": "Battery Replacement",
  "serviceDate": "2024-04-25",
  "cost": 150.00,
  "mileage": 5000,
  "mechanic": "Tesla Service Center",
  "notes": "Battery health check completed"
}
```

## 🎨 UI Features

- **Responsive Design**: Adapts to desktop and mobile screens
- **Color-Coded Elements**: Vehicle type badges (Gasoline/Electric) with distinct colors
- **Smooth Animations**: Fade-in transitions between pages
- **Intuitive Navigation**: Tab-based navigation for easy access
- **Data Validation**: Required fields and format validation on frontend and backend
- **Real-time Feedback**: Success and error messages for user actions

## 🔒 Data Persistence

- **Text File Storage**: Services and vehicles stored in plain text format
- **Automatic Backup**: Each save operation overwrites the file with complete data
- **UTF-8 Encoding**: Supports special characters
- **Pipe-Delimited Format**: Easy to parse and edit manually if needed

### File Format

**vehicles.txt:**
```
ID|Name|Make|Model|Year|Type
550e8400-e29b-41d4-a716-446655440000|My Car|Toyota|Camry|2020|GASOLINE
```

**services.txt:**
```
ID|VehicleID|VehicleName|ServiceType|ServiceDate|Cost|Mileage|Mechanic|Notes
6ba7b810-9dad-11d1-80b4-00c04fd430c8|550e8400-e29b-41d4-a716-446655440000|My Car|Oil Change|2024-04-20|50.00|50000|Joe's Auto|Regular maintenance
```

## 🛠️ Development

### Backend Technologies
- Spring Boot 3.0.0
- Java 11
- Gson (JSON processing)
- Maven

### Frontend Technologies
- HTML5
- CSS3 (Grid, Flexbox)
- Vanilla JavaScript (ES6+)
- Fetch API for HTTP requests

### OOP Principles Implemented
- **Encapsulation**: Private fields, getters/setters
- **Abstraction**: FileBasedRepository abstract class, ServiceReminder abstract class
- **Inheritance**: Repository implementations extend FileBasedRepository
- **Polymorphism**: Different reminder calculations based on vehicle type

## 🐛 Troubleshooting

### Backend won't start
- Ensure Java 11+ is installed: `java -version`
- Check Maven is installed: `mvn -version`
- Ensure port 8080 is not in use
- Check `application.properties` configuration

### Frontend not loading
- Ensure backend is running on port 8080
- Check browser console for CORS errors
- Verify `script.js` has correct API_BASE_URL

### Data not persisting
- Ensure `data/` directory exists
- Check file permissions on `services.txt` and `vehicles.txt`
- Verify backend has write access to the data directory

## 📄 License

This project is provided as-is for educational and personal use.

## 🤝 Contributing

Feel free to submit issues and enhancement requests!

## 📞 Support

For issues or questions, please check the project structure and API documentation above.