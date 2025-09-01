# Git Configuration Guide for MeowCDD Project

## 🎯 Overview
This guide explains how Git is configured for the MeowCDD (Child Development Service) project and provides best practices for version control.

## 📁 Project Structure
```
meowCDD/
├── .git/                    # Git repository data
├── .gitignore              # Git ignore rules
├── src/                    # Source code
│   ├── main/java/         # Java source files
│   └── resources/         # Configuration files
├── supabase/migrations/   # Database migration files
├── target/                # Compiled files (ignored by Git)
└── pom.xml               # Maven configuration
```

## 🔧 Git Configuration

### Remote Repository
- **URL**: https://github.com/meowcosmetic/meowCDD.git
- **Branch**: main
- **Status**: Up to date

### .gitignore Configuration
The project includes a comprehensive `.gitignore` file that excludes:
- Compiled Java classes (`*.class`)
- Maven build directory (`target/`)
- IDE-specific files (`.idea/`, `.vscode/`)
- Log files (`*.log`)
- Environment files (`.env`)
- Temporary files

## 📝 Git Workflow

### 1. Daily Development
```bash
# Check current status
git status

# Pull latest changes
git pull origin main

# Make your changes...

# Add files to staging
git add .

# Commit changes
git commit -m "feat: Add new feature description"

# Push to remote
git push origin main
```

### 2. Feature Development
```bash
# Create feature branch
git checkout -b feature/new-feature-name

# Make changes...

# Commit changes
git add .
git commit -m "feat: Add new feature"

# Push feature branch
git push origin feature/new-feature-name

# Create Pull Request on GitHub
# After review, merge to main
```

### 3. Bug Fixes
```bash
# Create hotfix branch
git checkout -b hotfix/bug-description

# Fix the bug...

# Commit fix
git add .
git commit -m "fix: Fix bug description"

# Push and create PR
git push origin hotfix/bug-description
```

## 📋 Commit Message Convention

We follow the [Conventional Commits](https://www.conventionalcommits.org/) standard:

### Types
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, etc.)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

### Examples
```bash
git commit -m "feat: Add multilingual support for DevelopmentalDomain"
git commit -m "fix: Resolve Jakarta EE import issues"
git commit -m "docs: Update API documentation"
git commit -m "refactor: Improve service layer architecture"
```

## 🚀 Recent Changes

### Latest Commit
- **Hash**: f219188
- **Message**: "feat: Add comprehensive library management system with multilingual support"
- **Files Changed**: 26 files, 4,782 insertions

### What Was Added
- ✅ SupportedFormat entity for managing file formats
- ✅ DevelopmentalDomain entity with multilingual support
- ✅ Book entity with relationships
- ✅ BookRating entity for reviews
- ✅ Complete CRUD operations
- ✅ SQL migrations
- ✅ .gitignore configuration
- ✅ Jakarta EE import fixes

## 🔍 Useful Git Commands

### Status and History
```bash
git status                    # Check current status
git log --oneline            # View commit history
git log --graph --oneline    # View commit graph
git diff                     # View unstaged changes
git diff --staged           # View staged changes
```

### Branch Management
```bash
git branch                   # List local branches
git branch -r               # List remote branches
git branch -a               # List all branches
git checkout <branch>       # Switch to branch
git branch -d <branch>      # Delete local branch
```

### Remote Operations
```bash
git remote -v               # View remote repositories
git fetch origin            # Fetch latest changes
git pull origin main        # Pull and merge changes
git push origin main        # Push changes to remote
```

## ⚠️ Important Notes

### Before Committing
1. **Always pull latest changes**: `git pull origin main`
2. **Check what you're committing**: `git status` and `git diff --staged`
3. **Write meaningful commit messages**: Follow conventional commits
4. **Don't commit compiled files**: They're automatically ignored

### File Types to Never Commit
- `.class` files (compiled Java)
- `target/` directory (Maven build output)
- `.env` files (environment variables)
- IDE-specific files
- Log files
- Temporary files

### Best Practices
1. **Commit frequently**: Small, focused commits are better than large ones
2. **Use descriptive branch names**: `feature/user-authentication`, `hotfix/login-bug`
3. **Review before pushing**: Always review your changes
4. **Keep main branch clean**: Use feature branches for development
5. **Update documentation**: When adding new features, update relevant docs

## 🆘 Troubleshooting

### Common Issues

#### 1. Merge Conflicts
```bash
# When you get merge conflicts
git status                    # See conflicted files
# Edit files to resolve conflicts
git add .                     # Stage resolved files
git commit                    # Complete the merge
```

#### 2. Undo Last Commit
```bash
git reset --soft HEAD~1      # Undo commit, keep changes staged
git reset --hard HEAD~1      # Undo commit, discard changes
```

#### 3. Discard Local Changes
```bash
git checkout -- <file>       # Discard changes in specific file
git reset --hard HEAD        # Discard all local changes
```

#### 4. Fix Wrong Remote URL
```bash
git remote set-url origin https://github.com/meowcosmetic/meowCDD.git
```

## 📞 Support

If you encounter Git issues:
1. Check this guide first
2. Use `git help <command>` for command help
3. Check [Git documentation](https://git-scm.com/doc)
4. Ask the team for assistance

---

**Last Updated**: September 2024
**Repository**: https://github.com/meowcosmetic/meowCDD.git

