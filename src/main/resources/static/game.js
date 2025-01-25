class TicTacToe {
    constructor() {
        this.board = document.getElementById('board');
        this.cells = document.querySelectorAll('.cell');
        this.resetButton = document.getElementById('resetButton');
        this.gameStatus = document.getElementById('gameStatus');
        this.currentPlayer = 'X';
        this.gameActive = true;

        this.initializeGame();
    }

    initializeGame() {
        this.cells.forEach(cell => {
            cell.addEventListener('click', () => this.handleCellClick(cell));
            cell.textContent = '';
        });

        this.resetButton.addEventListener('click', () => this.resetGame());
        this.updateStatus(`${this.currentPlayer}'s turn`);
    }

    async handleCellClick(cell) {
        if (!this.gameActive || cell.textContent !== '') return;

        const index = cell.dataset.index;
        
        try {
            const response = await fetch('/api/move', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    position: parseInt(index),
                    player: this.currentPlayer
                })
            });

            const gameState = await response.json();
            
            // Update the board
            cell.textContent = this.currentPlayer;
            
            if (gameState.winner) {
                this.gameActive = false;
                this.updateStatus(`Player ${gameState.winner} wins!`);
            } else if (gameState.draw) {
                this.gameActive = false;
                this.updateStatus("Game is a draw!");
            } else {
                this.currentPlayer = this.currentPlayer === 'X' ? 'O' : 'X';
                this.updateStatus(`${this.currentPlayer}'s turn`);
            }
        } catch (error) {
            console.error('Error:', error);
            this.updateStatus('An error occurred. Please try again.');
        }
    }

    async resetGame() {
        try {
            await fetch('/api/reset', { method: 'POST' });
            
            this.cells.forEach(cell => cell.textContent = '');
            this.currentPlayer = 'X';
            this.gameActive = true;
            this.updateStatus(`${this.currentPlayer}'s turn`);
        } catch (error) {
            console.error('Error resetting game:', error);
            this.updateStatus('Error resetting game. Please refresh the page.');
        }
    }

    updateStatus(message) {
        this.gameStatus.textContent = message;
    }
}

// Initialize the game when the page loads
document.addEventListener('DOMContentLoaded', () => {
    new TicTacToe();
}); 