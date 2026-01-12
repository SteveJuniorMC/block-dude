package com.blockdude.game.data

object LevelRepository {

    fun getAllLevels(): List<Level> = listOf(
        // Level 1
        Level.fromString(
            id = 1,
            name = "Level 1",
            levelString = """
                ################












                    #      #   D
                 B P#B     #  ##
                ################
            """
        ),

        // Level 2
        Level.fromString(
            id = 2,
            name = "Level 2",
            levelString = """
                ################










                 P
                ##B         ###
                #### #      ###D
                #### ###B ######
                ################
            """
        ),

        // Level 3
        Level.fromString(
            id = 3,
            name = "Level 3",
            levelString = """
                ################









                B
                ##
                  #        #
                   ##      #
                 P     B  B#   D
                ################
            """
        ),

        // Level 4
        Level.fromString(
            id = 4,
            name = "Level 4",
            levelString = """
                ################





                               D
                               #
                BP            ##
                BB       #   ###
                BBBB     ## ####
                #####   ### ####
                #####  #########
                ##### ##########
                ##### ##########
                ################
            """
        ),

        // Level 5
        Level.fromString(
            id = 5,
            name = "Level 5",
            levelString = """
                ################




                B
                BB  P
                BBB #    #
                #####    ## ###
                #####  #### ###
                ##### #########
                ##### #########
                ##### #########D
                ################
                ################
                ################
            """
        ),

        // Level 6 (was level 8 - easier)
        Level.fromString(
            id = 6,
            name = "Level 6",
            levelString = """
                ################
                #              #
                #         #B   #
                #       ###### #
                #      ####### #
                #     #      # #
                #    #       # #
                #B      P    # #
                ####    #    # #
                #####   ## B # #
                #   ##   ### #
                #   ### B### # D
                #   ########   #
                #B  #          #
                #BB          B #
                ################
            """
        ),

        // Level 7 (was level 6)
        Level.fromString(
            id = 7,
            name = "Level 7",
            levelString = """
                ################


                          #####
                         #    #
                        ##    #
                       ##     #
                       ##     #
                               D
                # ##           #
                #######        #
                #####  #     BB#
                            BBB#
                           BBBB#
                 B P #     BBBB#
                ################
            """
        ),

        // Level 8 (was level 7 - hardest)
        Level.fromString(
            id = 8,
            name = "Level 8",
            levelString = """
                ################

                           B
                       ########
                       #
                     B #  BB B
                B   #### #######
                BB     #
                ####   #
                      B#
                     BB#
                P  ##### #
                B ###### #
                ######## #  #
                ######## #  #  D
                ################
            """
        ),

        // Level 9
        Level.fromString(
            id = 9,
            name = "Level 9",
            levelString = """
                ################

                 BBB P
                 ######    ####
                 #      B     #D
                 #      BB    ##
                 #      ##
                 #       ##
                           #   B
                 #           BBB
                 #BB  #    BBBBB
                ####### ########
                ####### ########
                ####### ########
                ####### ########
                ################
            """
        ),

        // Level 10
        Level.fromString(
            id = 10,
            name = "Level 10",
            levelString = """
                ################

                          BB
                 #   #######
                  B
                 ########  B
                          ###
                         ##
                        ##
                       ##
                      ##
                    P##
                    ##      # #
                B   #      ## #
                B      B  ### #D
                ############# ##
            """
        )
    )
}
