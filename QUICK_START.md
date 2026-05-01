# Quick Start Guide

## 🚀 Start the Application

### Terminal 1: Start Backend
```bash
cd backend
mvn clean spring-boot:run
```
Backend will be available at: http://localhost:8080

### Terminal 2: Start Frontend
```bash
cd frontend
# Option 1: Using Python (if installed)
python3 -m http.server 3000

# Option 2: Using Node.js http-server
npx http-server -p 3000

# Option 3: Simply open index.html in browser directly
# (but API calls work best with a local server)
```
Frontend will be available at: http://localhost:3000 (or open index.html)

## ✅ Quick Test

1. **Open http://localhost:3000 in your browser**

2. **Add a Vehicle:**
   - Go to "Vehicles" tab
   - Click "+ Add New Vehicle"
   - Fill in: Name, Make, Model, Year, Type
   - Example: My Car | Toyota | Camry | 2020 | Gasoline
   - Click "Add Vehicle"

3. **Log a Service:**
   - Go to "Log Service" tab
   - Select the vehicle you just created
   - Choose service type: Oil Change
   - Enter today's date
   - Cost: 50
   - Mileage: 50000
   - Mechanic: Joe's Auto
   - Click "Log Service"

4. **View Analytics:**
   - Go to "Analytics" tab
   - See your first service logged!

## 📂 Data Files

After running the app, check the `data/` directory:
- `vehicles.txt` - Contains all vehicles
- `services.txt` - Contains all service records

These are plain text files that you can view directly.

## 🔧 Configuration

Backend configuration is in: `backend/src/main/resources/application.properties`

To change data storage location, edit:
```properties
app.data.path=data/services.txt
```

## 🌐 API Testing

Test the API endpoints directly:

```bash
# Get all vehicles
curl http://localhost:8080/api/vehicles

# Get all services
curl http://localhost:8080/api/services

# Create a vehicle
curl -X POST http://localhost:8080/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{"name":"My Car","make":"Toyota","model":"Camry","year":2020,"type":"GASOLINE"}'
```

## 🐛 Troubleshooting

### "Connection refused" error
- Make sure backend is running: `mvn clean spring-boot:run` in backend folder

### "CORS error" in browser console
- Backend CORS is configured to accept all origins
- Ensure frontend is making requests to `http://localhost:8080/api`

### Frontend blank page
- Check browser console (F12) for errors
- Ensure `script.js` can connect to backend
- Try opening index.html directly if not using a server

### Data not saving
- Check that `data/` directory exists
- Verify backend has write permissions
- Check backend console for errors

## 📚 Next Steps

- Customize vehicle types and service types in the code
- Add more analytics metrics
- Implement data export/import features
- Add backup and restore functionality
- Deploy to production server
