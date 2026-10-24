# My Creative Notes — Chrome Web Store submission guide
**Prepared:** 24 October 2026  
**Candidate:** v8.4 — *not submitted, not yet reviewed by Google*

## Listing details (copy into Chrome Web Store Developer Dashboard)

**Extension name:** My Creative Notes

**Short description (manifest):**
Organize notes into tabs and sections with custom icons, 15 color themes, and local backups. No account or cloud sync.

**Suggested category:** Productivity

**Language:** English (United States)

**Pricing:** Free

**Single purpose:** Help users write, edit, and organize their own notes in a Chrome tab.

### Long description
My Creative Notes is a colorful, easy-to-use workspace for organizing personal notes right inside Chrome.

Keep different topics separate with customizable tabs and sections. Give tabs and sections their own icons, add and edit notes, format text, and find what you need with search. Pick from 15 color themes to make your workspace your own.

**Features**
- Create, rename, reorganize, and delete tabs and sections.
- Write and edit notes with headings, formatting, and lists.
- Personalize your workspace with icons and 15 color themes.
- Quickly access your notes from a pinned Chrome extension icon.
- Export a JSON backup and import it when you choose.

**Your notes remain yours.** No account or automatic cloud sync is needed. Notes and preferences are saved locally in your Chrome browser profile. Your website installation and Chrome extension store notes separately; to move notes between them, export a backup and import it yourself.

**Important:** Export a backup before uninstalling the extension, clearing Chrome data, or changing devices. Uninstalling can remove local notes.

### Publisher and URLs
- **Publisher:** BroKaz App Hub (confirm desired public developer display name before submitting)
- **Homepage:** https://brokaz-app-hub.github.io/creative-notes/
- **Privacy policy:** https://brokaz-app-hub.github.io/creative-notes/extension-privacy.html
- **Support contact:** **To be supplied by publisher** in Developer Dashboard; do not invent a public email address.

## Privacy practices draft — review against the live Developer Dashboard
- The extension stores user-entered tabs, sections, notes, formatting, choices of icons/themes and optional display name locally within its Chrome extension origin.
- No account, analytics service, advertising SDK or cloud sync is provided.
- No automatically transmitted note contents to BroKaz App Hub or third parties.
- Export/import and sharing are **user initiated**; exported files can be saved or handed to a user-chosen application.
- **No Chrome permissions requested** in the manifest.
- Local user-generated content must be disclosed accurately under whatever categories the Dashboard presents. Read its wording; do not automatically answer "no user data" without checking the definitions.
- The extension's core purpose is personal note organization; any future collection or data-sharing changes require updated user disclosures and policy.
- The extension does not independently encrypt its locally stored notes.
- Policy page must be publicly accessible before Google reviews the listing.

## Required real screenshots
The Web Store accepts **1280×800** or **640×400** screenshots. Include at least **one real, current screenshot** of the extension, preferably three:
1. Main app showing sample, non-personal notes and the sidebar.
2. Tab editor or note editor showing how editing works.
3. Settings showing some of the 15 color themes.

Use the **actual v8.4 extension UI**. Avoid personal/work note content, passwords, identifiable donor details, or fabricated feature mockups. Avoid blurry or distorted images. The published store icon comes from packaged icons/icon128.png.

## Final local test checklist
- [ ] Install/update **v8.4** in the existing unpacked extension folder (back up notes first).
- [ ] Toolbar icon opens My Creative Notes in a Chrome tab.
- [ ] Create first tab → immediately double-click it → Edit Tab works.
- [ ] Create and edit a note including its name; close and reopen Chrome; data persists.
- [ ] Make a backup, then import it into a fresh test Chrome profile.
- [ ] Choose themes, check icon picker, and check keyboard navigation.
- [ ] Verify the visible Privacy Policy link and content.
- [ ] Test a separate Chrome profile to verify personal note data does not mix.
- [ ] Confirm Chrome extensions page reports no errors.
- [ ] Capture at least one true, current 1280×800 or 640×400 screenshot.

## Developer Dashboard steps (user action required)
1. Sign in to https://chrome.google.com/webstore/devconsole with the Google account you want to use as the **permanent publisher account**.
2. Pay the one-time developer registration fee if not already registered. Verify the exact amount shown in the Dashboard before payment.
3. Upload `My-Creative-Notes-Chrome-Extension-v8.4-STORE-CANDIDATE.zip` (contains manifest.json at the archive root).
4. Enter name, long description, category, language, homepage, screenshots, contact information and public privacy policy URL.
5. Complete the Privacy Practices section accurately and certify the extension's limited use of data.
6. Choose visibility (prefer **Private or Unlisted** for an initial controlled release, if available and appropriate). Note: these distributions still require Google's review.
7. Submit for Google's review. **No submission has been made yet** and approval cannot be guaranteed.

## Sources
- https://developer.chrome.com/docs/webstore/prepare
- https://developer.chrome.com/docs/webstore/best-listing
- https://developer.chrome.com/docs/webstore/cws-dashboard-privacy
- https://developer.chrome.com/docs/webstore/program-policies/policies/
- https://developer.chrome.com/blog/cws-policy-updates-2026

**Files reside on a dedicated GitHub test branch, separate from the live web app.**
