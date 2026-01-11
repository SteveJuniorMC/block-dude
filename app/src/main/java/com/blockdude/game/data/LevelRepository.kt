package com.blockdude.game.data

object LevelRepository {

    fun getAllLevels(): List<Level> = listOf(
        // Level 1: Tutorial - Basic movement
        Level.fromString(
            id = 1,
            name = "First Steps",
            levelString = """
                ##########
                #D       #
                ###      #
                #        #
                #   P    #
                ##########
            """
        ),

        // Level 2: Tutorial - Climbing
        Level.fromString(
            id = 2,
            name = "Climb Up",
            levelString = """
                ##########
                #      D #
                #      ###
                #        #
                #   ##   #
                # P      #
                ##########
            """
        ),

        // Level 3: Tutorial - Push a block
        Level.fromString(
            id = 3,
            name = "Push It",
            levelString = """
                ###########
                #D        #
                ###       #
                #     B   #
                # P  ##   #
                ###########
            """
        ),

        // Level 4: Pick up and place
        Level.fromString(
            id = 4,
            name = "Lift Off",
            levelString = """
                ###########
                #        D#
                #       ###
                #    ##   #
                # P  B    #
                ###########
            """
        ),

        // Level 5: Two blocks
        Level.fromString(
            id = 5,
            name = "Double",
            levelString = """
                ############
                #D         #
                ###        #
                #      B   #
                #   B  #   #
                # P  ###   #
                ############
            """
        ),

        // Level 6: Stack blocks
        Level.fromString(
            id = 6,
            name = "Stacker",
            levelString = """
                ############
                #         D#
                #        ###
                #        # #
                #   B B  # #
                # P ######+#
                ############
            """
        ),

        // Level 7: Bridge building
        Level.fromString(
            id = 7,
            name = "Bridge",
            levelString = """
                #############
                #D          #
                ###         #
                #           #
                #     ###   #
                # P B     B #
                #############
            """
        ),

        // Level 8: Precision required
        Level.fromString(
            id = 8,
            name = "Precision",
            levelString = """
                ##############
                #           D#
                #          ###
                #  ###       #
                #        #   #
                # P  B  B#   #
                ##############
            """
        ),

        // Level 9: The pit
        Level.fromString(
            id = 9,
            name = "The Pit",
            levelString = """
                ##############
                #D           #
                ###          #
                #      B     #
                #   ####  B  #
                # P      ####$
                ##############
            """
        ),

        // Level 10: Tower climb
        Level.fromString(
            id = 10,
            name = "Tower",
            levelString = """
                ###############
                #            D#
                #           ###
                #    B      # #
                #   ##  B   # #
                # P ###  #### #
                ###############
            """
        ),

        // Level 11: The challenge
        Level.fromString(
            id = 11,
            name = "Challenge",
            levelString = """
                ################
                #D             #
                ###            #
                #       ##     #
                #   B       B  #
                #  ###   ###   #
                # P          B #
                ################
            """
        )
    )
}
