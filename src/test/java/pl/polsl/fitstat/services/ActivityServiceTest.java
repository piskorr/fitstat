package pl.polsl.fitstat.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.polsl.fitstat.dtos.ActivityDTO;
import pl.polsl.fitstat.models.ActivityEntity;
import pl.polsl.fitstat.repositories.ActivityRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class ActivityServiceTest {

    private static ActivityRepository mockRepository;
    private static ActivityService testService;

    @BeforeAll
    public static void setup() {
        mockRepository = mock(ActivityRepository.class);
        testService = new ActivityService(mockRepository);
    }

    @Test
    void get_All_Activities_With_Empty_Repo() {
        when(mockRepository.findAll()).thenReturn(Collections.emptyList());
        List<ActivityDTO> result = testService.getAllActivities();
        assertThat(result).isEqualTo(Collections.emptyList());
        assertThat(result.size()).isEqualTo(Integer.valueOf(0));
    }

    @Test
    void get_All_Activities_With_Deleted_Repo() {
        ActivityEntity mockActivity = mock(ActivityEntity.class);
        List<ActivityEntity> dummyList = List.of(mockActivity, mockActivity, mockActivity);

        when(mockActivity.isDeleted()).thenReturn(true);
        when(mockRepository.findAll()).thenReturn(dummyList);
        List<ActivityDTO> result = testService.getAllActivities();
        assertThat(result).isEqualTo(Collections.emptyList());
        assertThat(result.size()).isEqualTo(Integer.valueOf(0));
    }

    @Test
    void get_Activity_By_Id_With_Wrong_Id() {
        ActivityRepository mockRepository = mock(ActivityRepository.class);


    }

    @Test
    void getActivityById() {
    }

    @Test
    void getActivityByName() {
    }

    @Test
    void getActivityByNameAndMap() {
    }

    @Test
    void getActivityByIdAndMap() {
    }

    @Test
    void getAllActivities() {
    }

    @Test
    void addNewActivity() {
    }

    @Test
    void updateActivityById() {
    }

    @Test
    void deleteActivityById() {
    }
}