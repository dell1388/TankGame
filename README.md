<img width="384" alt="Screenshot 2025-05-02 000100" src="https://github.com/user-attachments/assets/b42eafaa-3c73-46e5-9106-41f200d0be56" />


Instruction: 
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


            Player uses "W" and "S" to move the tank forward and back. And uses "A" and "D" to change the direction of the tank. 
            The aim direction is following with mouth, and hit other tanks with "SPACE" to earn the score


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


 
| Ben | David | Josh |
|----------|----------|----------|
| 71 hrs | 45 hrs | 30 hrs |
| [Journal](https://github.com/orgs/bjucps209/projects/14) | [Journal](https://github.com/orgs/bjucps209/projects/15) |[Journal](https://github.com/orgs/bjucps209/projects/16) |

[Video(needs updating)](https://github.com/bjucps209/group-project-tankers-of-norvinsk/blob/main/sprint2.mp4)

After clicking play, you will spawn as a tank in the middle of the map. Use WASD to move around and space to shoot at your cursor. Shoot at other tanks to gain score, and don't get hit. You have three lives.  
  
**Known Bugs:**  
 * Upon approaching the edge of the map, the method that compensates for player movement bugs and everything except for the player moves away from the map border at incredible speeds. This effect is proportion to the amount which the player attempts to approach the map border. A fix will be implemented soon, but for now, avoid the edge.  
 * If the window in which the game is being played exceeds the viewport of the map, the logic that calculates the angle to the mouse will be faulty and your tank will be unable to aim correctly. The guilty method is known, but some more time is needed debugging. Fix: Be sure your window is smaller than the viewport: **If you see white, you won't shoot right.**
 * Sometimes, an AI tank should spawn, but it immediately kills itself by firing. We aren't sure why this is happening, but it is probably due to some tanks having longer hitboxes than others, tho that should be accounted for. 
