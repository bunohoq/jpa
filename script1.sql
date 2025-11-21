drop table tblItem;

create table tblItem (
    seq number primary key,
    name varchar2(100) not null,
    price number not null,
    color varchar2(50) check (color in ('black', 'white', 'yellow', 'red', 'blue')) not null,
    qty number null,
    description varchar2(1000)
);
create sequence seqItem start with 31;


insert into tblItem (seq, name, price, color, qty, description) values (1, '노트북', 1200000, 'black', 10, '강력한 성능의 최신형 노트북입니다. 고성능 제품입니다.');
insert into tblItem (seq, name, price, color, qty, description) values (2, '스마트폰', 850000, 'white', 50, '최신 기능을 탑재한 스마트폰입니다. 스마트한 기능을 자랑합니다.');
insert into tblItem (seq, name, price, color, qty, description) values (3, '태블릿', 550000, 'yellow', 30, '가벼운 무게로 휴대가 간편한 태블릿입니다. 스마트한 기기입니다.');
insert into tblItem (seq, name, price, color, qty, description) values (4, '모니터', 300000, 'red', 20, '24인치 고화질 모니터로 눈이 편안합니다.');
insert into tblItem (seq, name, price, color, qty, description) values (5, '키보드', 85000, 'blue', 100, '기계식 스위치로 부드러운 타이핑감을 제공합니다.');
insert into tblItem (seq, name, price, color, qty, description) values (6, '마우스', 40000, 'black', 200, '무선 연결과 인체공학적 디자인 마우스.');
insert into tblItem (seq, name, price, color, qty, description) values (7, '프린터', 220000, 'white', 15, '빠른 인쇄 속도의 레이저 프린터입니다. 고성능 장비.');
insert into tblItem (seq, name, price, color, qty, description) values (8, '헤드폰', 150000, 'black', 40, '노이즈 캔슬링 기능이 탑재된 헤드폰.');
insert into tblItem (seq, name, price, color, qty, description) values (9, '스피커', 180000, 'red', null, '블루투스 지원 휴대용 스피커입니다.');
insert into tblItem (seq, name, price, color, qty, description) values (10, '웹캠', 120000, 'white', 35, '고화질 영상 촬영이 가능한 웹캠입니다.');
insert into tblItem (seq, name, price, color, qty, description) values (11, '외장하드', 170000, 'blue', 60, '1TB 대용량 외장 하드 디스크입니다.');
insert into tblItem (seq, name, price, color, qty, description) values (12, 'USB 플래시 드라이브', 35000, 'yellow', 150, '32GB USB 플래시 드라이브, 빠른 전송 속도.');
insert into tblItem (seq, name, price, color, qty, description) values (13, '마이크', 110000, 'black', 10, null);
insert into tblItem (seq, name, price, color, qty, description) values (14, '충전기', 60000, 'white', 100, '고속 충전이 가능한 스마트폰 충전기. 스마트한 기술.');
insert into tblItem (seq, name, price, color, qty, description) values (15, '라우터', 130000, 'blue', 18, '빠른 속도의 와이파이 라우터입니다.');
insert into tblItem (seq, name, price, color, qty, description) values (16, '스마트워치', 280000, 'red', 22, '건강 관리 기능이 풍부한 스마트워치. 스마트 기기.');
insert into tblItem (seq, name, price, color, qty, description) values (17, '카메라', 750000, 'black', 8, '고성능 디지털 카메라입니다. 사진 촬영에 탁월한 성능.');
insert into tblItem (seq, name, price, color, qty, description) values (18, '프로젝터', 400000, 'white', null, '집에서 즐기는 홈 시네마 프로젝터.');
insert into tblItem (seq, name, price, color, qty, description) values (19, '책상 램프', 55000, 'yellow', null, null);
insert into tblItem (seq, name, price, color, qty, description) values (20, '게임 콘솔', 550000, 'black', 12, '최신 게임을 즐길 수 있는 콘솔.');
insert into tblItem (seq, name, price, color, qty, description) values (21, 'VR 헤드셋', 620000, 'white', 6, '가상현실을 체험할 수 있는 VR 헤드셋.');
insert into tblItem (seq, name, price, color, qty, description) values (22, '전기 스쿠터', 850000, 'blue', 4, '도심형 전기 스쿠터, 친환경 이동수단.');
insert into tblItem (seq, name, price, color, qty, description) values (23, '커피메이커', 95000, 'red', 20, '자동으로 커피를 내리는 커피메이커.');
insert into tblItem (seq, name, price, color, qty, description) values (24, '공기청정기', 180000, 'white', 14, 'HEPA 필터가 장착된 공기청정기.');
insert into tblItem (seq, name, price, color, qty, description) values (25, '믹서기', 120000, 'black', 25, '고속으로 작동하는 믹서기입니다.');
insert into tblItem (seq, name, price, color, qty, description) values (26, '진공 청소기', 230000, 'blue', 10, '무봉투 타입 진공 청소기.');
insert into tblItem (seq, name, price, color, qty, description) values (27, '스마트 조명', 85000, 'yellow', 30, '와이파이 연결 스마트 전구. 스마트 기능 탑재.');
insert into tblItem (seq, name, price, color, qty, description) values (28, '헤어 드라이기', 65000, 'red', 20, '고성능 전문가용 헤어 드라이기.');
insert into tblItem (seq, name, price, color, qty, description) values (29, '전기 주전자', 85000, 'white', 50, '자동 차단 기능 전기 주전자입니다.');
insert into tblItem (seq, name, price, color, qty, description) values (30, '블루투스 이어폰', 120000, 'black', 45, '무선으로 편리한 블루투스 이어폰입니다. 청음 성능 탁월.');

commit;

select * from tblItem order by price desc;

select * from tblItem where seq = 52;

select * from tblItem where color = 'black';