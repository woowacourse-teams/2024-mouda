import * as S from './MoimList.style';
import { meetings } from './mockData';

export default function MoimList() {
  return (
    <>
      <div css={S.containerStyle}>
        <header css={S.headerStyle}>
          <h1 css={S.logoStyle}>우아한테크코스</h1>
        </header>
        <main css={S.mainStyle}>
          <nav css={S.navStyle}>
            <p css={S.navItemStyle}>모임목록</p>
            <p css={S.navItemStyle}> 나의모임</p>
          </nav>

          <section>
            {meetings.map((meeting) => (
              <div key={meeting.id} css={S.meetingCardStyle}>
                <h2>{meeting.title}</h2>
                <p className="date">{meeting.date}</p>
                <p>{meeting.location}</p>
                <p>
                  최대 {meeting.maxParticipants}명 (현재{' '}
                  {meeting.currentParticipants}명)
                </p>
              </div>
            ))}
          </section>
        </main>

        {/* fixed button */}
        <div>
          <button css={S.plusButtonStyle}>+</button>
        </div>
      </div>
    </>
  );
}
