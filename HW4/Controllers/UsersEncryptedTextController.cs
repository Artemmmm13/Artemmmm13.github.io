using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using HW4.Data;
using HW4.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore.Metadata.Internal;

namespace HW4.Controllers
{
    public class UsersEncryptedTextController : Controller
    {
        private readonly ApplicationDbContext _context;

        public UsersEncryptedTextController(ApplicationDbContext context)
        {
            _context = context;
        }

        // GET: UsersEncryptedText
        public async Task<IActionResult> Index()
        {
              return _context.UserEncryptedTextMappings != null ? 
                          View(await _context.UserEncryptedTextMappings.ToListAsync()) :
                          Problem("Entity set 'ApplicationDbContext.UserEncryptedTextMappings'  is null.");
        }

        // GET: UsersEncryptedText/Details/5
        public async Task<IActionResult> Details(Guid? id)
        {
            if (id == null || _context.UserEncryptedTextMappings == null)
            {
                return NotFound();
            }

            var userEncryptedTextMapping = await _context.UserEncryptedTextMappings
                .FirstOrDefaultAsync(m => m.UserToEncryptedTextMappingId == id);
            if (userEncryptedTextMapping == null)
            {
                return NotFound();
            }

            return View(userEncryptedTextMapping);
        }

        // GET: UsersEncryptedText/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: UsersEncryptedText/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("UserToEncryptedTextMappingId,TextCreatorId,EncryptedText")] UserEncryptedTextMapping userEncryptedTextMapping)
        {
            if (ModelState.IsValid)
            {
                userEncryptedTextMapping.UserToEncryptedTextMappingId = Guid.NewGuid();
                _context.Add(userEncryptedTextMapping);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(userEncryptedTextMapping);
        }

        // GET: UsersEncryptedText/Edit/5
        public async Task<IActionResult> Edit(Guid? id)
        {
            if (id == null || _context.UserEncryptedTextMappings == null)
            {
                return NotFound();
            }

            var userEncryptedTextMapping = await _context.UserEncryptedTextMappings.FindAsync(id);
            if (userEncryptedTextMapping == null)
            {
                return NotFound();
            }
            return View(userEncryptedTextMapping);
        }

        // POST: UsersEncryptedText/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(Guid id, [Bind("UserToEncryptedTextMappingId,TextCreatorId,EncryptedText")] UserEncryptedTextMapping userEncryptedTextMapping)
        {
            if (id != userEncryptedTextMapping.UserToEncryptedTextMappingId)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(userEncryptedTextMapping);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!UserEncryptedTextMappingExists(userEncryptedTextMapping.UserToEncryptedTextMappingId))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(userEncryptedTextMapping);
        }

        // GET: UsersEncryptedText/Delete/5
        public async Task<IActionResult> Delete(Guid? id)
        {
            if (id == null || _context.UserEncryptedTextMappings == null)
            {
                return NotFound();
            }

            var userEncryptedTextMapping = await _context.UserEncryptedTextMappings
                .FirstOrDefaultAsync(m => m.UserToEncryptedTextMappingId == id);
            if (userEncryptedTextMapping == null)
            {
                return NotFound();
            }

            return View(userEncryptedTextMapping);
        }

        // POST: UsersEncryptedText/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(Guid id)
        {
            if (_context.UserEncryptedTextMappings == null)
            {
                return Problem("Entity set 'ApplicationDbContext.UserEncryptedTextMappings'  is null.");
            }
            var userEncryptedTextMapping = await _context.UserEncryptedTextMappings.FindAsync(id);
            if (userEncryptedTextMapping != null)
            {
                _context.UserEncryptedTextMappings.Remove(userEncryptedTextMapping);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool UserEncryptedTextMappingExists(Guid id)
        {
          return (_context.UserEncryptedTextMappings?.Any(e => e.UserToEncryptedTextMappingId == id)).GetValueOrDefault();
        }
        
        
        
    }
    
}
