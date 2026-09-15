struct station {
	int students;
	int seats;
	int nowBoarding;
	pthread_mutex_t lock;
	pthread_cond_t moreSeats, moreStudents;
};

void
station_init(struct station *station)
{
	station->students = 0;
	station->seats = 0;
	station->nowBoarding = 1;
	pthread_mutex_init(&station->lock, NULL);
	pthread_cond_init(&station->moreSeats, NULL);
	pthread_cond_init(&station->moreStudents, NULL);
}

void
station_load_bus(struct station *station, int count)
{
	pthread_mutex_lock(&station->lock);
	station->seats = count;
	while(station->seats > 0 && station->students > 0){
		pthread_cond_broadcast(&station->moreSeats);
		pthread_cond_wait(&station->moreStudents, &station->lock);
	}
	station->seats = 0;
	pthread_mutex_unlock(&station->lock);
	return;
}

int
station_wait_for_bus(struct station *station, int myticket, int myid)
{
	pthread_mutex_lock(&station->lock);
	station->students += 1;
	while(station->nowBoarding != myticket || station->seats == 0){
		pthread_cond_wait(&station->moreSeats, &station->lock);
	}
	station->seats -= 1;
	station->students -= 1;
	int boarded = station->nowBoarding++;
	pthread_cond_signal(&station->moreStudents);
	pthread_mutex_unlock(&station->lock);
	return boarded;
}
