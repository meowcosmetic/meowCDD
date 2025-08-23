/**
 * MeowCDD Logout Component
 * Component JavaScript để quản lý logout trong frontend
 */

class MeowCDDLogout {
    constructor(config = {}) {
        this.apiBase = config.apiBase || 'http://localhost:8101/api/v1';
        this.onLogoutSuccess = config.onLogoutSuccess || this.defaultLogoutSuccess;
        this.onLogoutError = config.onLogoutError || this.defaultLogoutError;
        this.onStatusChange = config.onStatusChange || this.defaultStatusChange;
        
        this.isAuthenticated = false;
        this.currentUser = null;
        
        // Auto-check status on init
        this.checkStatus();
    }

    /**
     * Login user
     * @param {string} username 
     * @param {string} password 
     * @returns {Promise}
     */
    async login(username, password) {
        try {
            const response = await fetch(`${this.apiBase}/auth/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`,
                credentials: 'include'
            });

            const data = await response.json();
            
            if (data.success) {
                this.isAuthenticated = true;
                this.currentUser = data.user;
                this.onStatusChange(true, data.user);
                return { success: true, user: data.user };
            } else {
                return { success: false, message: data.message };
            }
        } catch (error) {
            return { success: false, message: error.message };
        }
    }

    /**
     * Logout user
     * @returns {Promise}
     */
    async logout() {
        try {
            const response = await fetch(`${this.apiBase}/auth/logout`, {
                method: 'POST',
                credentials: 'include'
            });

            const data = await response.json();
            
            if (data.success) {
                this.isAuthenticated = false;
                this.currentUser = null;
                this.onLogoutSuccess(data.user);
                this.onStatusChange(false, null);
                return { success: true, user: data.user };
            } else {
                this.onLogoutError(data.message);
                return { success: false, message: data.message };
            }
        } catch (error) {
            this.onLogoutError(error.message);
            return { success: false, message: error.message };
        }
    }

    /**
     * Check authentication status
     * @returns {Promise}
     */
    async checkStatus() {
        try {
            const response = await fetch(`${this.apiBase}/auth/status`, {
                credentials: 'include'
            });

            const data = await response.json();
            
            this.isAuthenticated = data.authenticated;
            this.currentUser = data.username || null;
            
            this.onStatusChange(data.authenticated, data.username);
            return data;
        } catch (error) {
            this.isAuthenticated = false;
            this.currentUser = null;
            this.onStatusChange(false, null);
            return { authenticated: false, error: error.message };
        }
    }

    /**
     * Get current user info
     * @returns {Promise}
     */
    async getUserInfo() {
        try {
            const response = await fetch(`${this.apiBase}/auth/user`, {
                credentials: 'include'
            });

            const data = await response.json();
            return data;
        } catch (error) {
            return { authenticated: false, error: error.message };
        }
    }

    /**
     * Check if user is authenticated
     * @returns {boolean}
     */
    isLoggedIn() {
        return this.isAuthenticated;
    }

    /**
     * Get current user
     * @returns {string|null}
     */
    getCurrentUser() {
        return this.currentUser;
    }

    // Default callback handlers
    defaultLogoutSuccess(user) {
        console.log(`User ${user} logged out successfully`);
    }

    defaultLogoutError(message) {
        console.error('Logout failed:', message);
    }

    defaultStatusChange(isAuthenticated, username) {
        console.log(`Authentication status changed: ${isAuthenticated ? 'Logged in' : 'Logged out'}`, username);
    }
}

// Example usage:
/*
const auth = new MeowCDDLogout({
    apiBase: 'http://localhost:8101/api/v1',
    onLogoutSuccess: (user) => {
        console.log(`Goodbye ${user}!`);
        // Redirect to login page
        window.location.href = '/login.html';
    },
    onLogoutError: (message) => {
        alert('Logout failed: ' + message);
    },
    onStatusChange: (isAuthenticated, username) => {
        // Update UI based on authentication status
        const logoutBtn = document.getElementById('logoutBtn');
        const userInfo = document.getElementById('userInfo');
        
        if (isAuthenticated) {
            logoutBtn.style.display = 'block';
            userInfo.textContent = `Welcome, ${username}!`;
        } else {
            logoutBtn.style.display = 'none';
            userInfo.textContent = 'Please login';
        }
    }
});

// Logout button click handler
document.getElementById('logoutBtn').addEventListener('click', async () => {
    const result = await auth.logout();
    if (result.success) {
        // Logout successful
    }
});

// Check status periodically
setInterval(() => {
    auth.checkStatus();
}, 60000); // Check every minute
*/

// Export for use in modules
if (typeof module !== 'undefined' && module.exports) {
    module.exports = MeowCDDLogout;
}
